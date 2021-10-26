package com.powernode.interceptor;

import com.powernode.beans.User;
import com.powernode.controller.LoginController;
import com.powernode.exception.MyLoginException;
import com.powernode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //System.out.println("请求到达拦截器");
        //在这里会拦截所有除登录请求的请求，在这里判断Session'中是否有用户信息
        //如果有，放行，如果没有，拦截，并强制跳转到登录界面
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if(user==null){
            //System.out.println("Session中没有用户信息，被拦截,强制跳转到登录界面");
            //强制跳转到登录界面
            /*response.sendRedirect("/user/login");
            return false;*/


            //Session中没有信息，但是还是要判断Cookie中有没有信息，如果有完成免登录效果
            String username=null;
            String password=null;
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if(LoginController.LOGINACT.equals(cookie.getName())){
                    //如果这个Cookie的键名字和登录的账户Cookie一致，取出来
                    username=cookie.getValue();
                }
                if(LoginController.LOGINPWD.equals(cookie.getName())){
                    //如果这个Cookie的键名字和登录的密码Cookie一致，取出来
                    password=cookie.getValue();
                }
            }


            //判断COOKie是否存在
            if(username!=null && password!=null){
                //验证账号是否仍然有效
                try{
                    //账号可能会失效，业务逻辑验证会抛出自定义MyLoginException异常
                    //如果不通过try catch抓取，就会直接被控制器增强的异常处理机制捕获
                    //但是控制器增强只会给前端的ajax响应数据，不能强制进行跳转功能，
                    //因为try catch捕获异常的优先级大于控制器增强，所以try捕获到了，控制器增强就不会捕获到异常
                    //由try catch来进行异常的捕获后处理：进行跳转
                    User login = userService.login(username, password, request.getRemoteAddr());


                    //如果账号有效，开始填充Session，放行，Cookie免登录效果实现
                    //如果走到这里，说明账号有效，没有异常
                    session.setAttribute("user",login);
                    return true;
                }catch (MyLoginException e){
                    //控制台打印异常信息
                    System.out.println(e.getMessage());
                    //进行跳转
                    response.sendRedirect("/user/login");
                    //拦截
                    return false;
                }



            }else{
                //到这里说明既没有Session中用户信息，也不存在Cookie，强制重定向到登录界面，拦截
                response.sendRedirect("/user/login");
                return false;
            }

        }
        //System.out.println("Session中有用户信息，放行");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
