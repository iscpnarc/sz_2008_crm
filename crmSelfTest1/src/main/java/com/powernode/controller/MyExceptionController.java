package com.powernode.controller;

import com.powernode.exception.MyLoginException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

//2全局增强的异常处理机制，虽然也是一个控制器，但是区别于正常的控制器，这个控制器的注解是@ControllerAdvice
@ControllerAdvice
public class MyExceptionController {

    //全局异常处理机制,这个注解全局监控异常，只要发生  MyLoginException  这个类型的自定义异常，就会跳转到这个异常处理机制的办法
    //还需要相应到前端ajax中
    @ExceptionHandler(MyLoginException.class)
    @ResponseBody
    public Map myLoginExp(MyLoginException e){

        //王map集合中存储键为msg的属性，内容就是异常所携带的一句话
        Map map = new HashMap();
        System.out.println(e.getMessage());
        map.put("msg",e.getMessage());

        return map;
    }
}
