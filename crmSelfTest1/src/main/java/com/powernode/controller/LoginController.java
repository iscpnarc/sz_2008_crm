package com.powernode.controller;

import com.powernode.beans.User;
import com.powernode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    public static final String SESSION_USER="user";
    public static final String LOGINACT="userAct";
    public static final String LOGINPWD="userPwd";

    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest request;

    @Resource
    HttpServletResponse response;


    //设置注销按钮
    //点击注销登录，要求请求Session中用户信息，清除Cookie，并且强制跳转到登录界面
    @RequestMapping("/user/logout.do")
    public String logoutAction(){

        //清除Session中信息
        request.getSession().removeAttribute(LoginController.SESSION_USER);

        //清除Cookie
        Cookie userAct = new Cookie(LoginController.LOGINACT,null);
        userAct.setMaxAge(0);
        userAct.setPath("/");
        Cookie userPwd = new Cookie(LoginController.LOGINPWD,null);
        userPwd.setMaxAge(0);
        userPwd.setPath("/");
        response.addCookie(userAct);
        response.addCookie(userPwd);

        //跳转登录界面
        return "login";
    }

    //跳转到登陆界面
    @RequestMapping("/user/login")
    public String test1(){
        return "login";
    }

    //页面填写账号密码，点击登录，前端ajax发送请求到这，携带数据：loginAct、loginPwd、复选框(暂时没有考虑)
    //通过方法中的参数，接收来自页面的数据
    //根据前端的需求，响应回前端必须要两个数据，msg和success，所以返回类型是map集合，不能是String
    //所以设想的场景是：成功，直接跳转到首页，失败，跳出弹窗阐述失败的原因
    @RequestMapping("/user/login.do")
    @ResponseBody
    public Map loginAction(String username, String password ,String autoLogin){
        //调用业务方法，验证登录界面发送的账号密码是否正确，状态是否无误
        //因为验证账号正确与否还需要请求的ip地址
        String remoteAddr = request.getRemoteAddr();
        //System.out.println(remoteAddr);

        User login = userService.login(username, password, remoteAddr);


        //想要做勾选免登录按钮，以及未登录禁止访问其他页面的功能
        //todo 1、未登录禁止访问其他页面的功能，需要将登录成功者的信息记录到Session作用域中
        //        当用户想要跳过登录步骤，直接通过地址URL访问后面的路径，就校验他的Session信息，
        //        观察其是否已经登录，未登录，强制返回登录界面
        //        现在，暂时只是保存登录者的信息，在前端界面显示出来
        HttpSession session = request.getSession();
        session.setAttribute("user",login);


        //todo 2、当在登录界面都选免登录按钮的时候，点击登录，前端ajax除了携带账户和密码，还会携带复选框的勾选状态
        //      在controller控制器中进行判断，如果勾选了，将信息存储进Cookie中，然后存储在浏览器的硬盘中
        //      可以直接通过参数接收来自前端ajax请求的数据，除了用户和密码，还有复选框的状态autoLogin
        if("1".equals(autoLogin)){
            Cookie userAct = new Cookie("userAct",login.getLoginAct());
            userAct.setMaxAge(10*24*60*60);
            userAct.setPath("/");

            Cookie userPwd = new Cookie("userPwd",login.getLoginPwd());
            userPwd.setMaxAge(10*24*60*60);
            userPwd.setPath("/");

            //通过response响应对象，将这些cookie存储进浏览器的硬盘中
            response.addCookie(userAct);
            response.addCookie(userPwd);
        }


        Map map=new HashMap();
        //如果登录成功，键msg中没有消息，键success中值为true，前端ajax请求实现首页跳转
        //走到这，那就是登录成功
        map.put("success",true);
        return map;
    }



    //查询特殊格式的登录用户getAllUserName.json
    //data: ["工号 | zhangsan", "工号 | lisi", ...]
    @RequestMapping("/user/getAllUserName.json")
    @ResponseBody
    public List<String> getAllUserNameJSON(){
        List<String> userString = userService.getUserString();
        //System.out.println(userString);
        return userString;
    }
}
