package com.powernode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ViewController {
    @Autowired
    HttpServletRequest request;

    //视图控制器，将前端的定位地址作为请求名称，通过控制器，转到真正给的jsp页面
    //根目录所有文件以及其子文件夹下的以.html结尾的请求名称，都通过这个控制器的方法响应
    @RequestMapping("/**/*.html")
    public String viewControllerAction(){
        //通过request对象获取请求的uri地址
        String requestURI = request.getRequestURI();
        //System.out.println(requestURI);
        //通过字符串的切割完成想要跳转地址的格式获取
        String substring = requestURI.substring(0, requestURI.lastIndexOf("."));

        return substring;
    }


    //直接访问根目录，强制其跳转到登录界面
    @RequestMapping("/")
    public String root(){
        return "login";
    }

}
