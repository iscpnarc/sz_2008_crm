package com.powernode.exception;
//自定义异常
public class MyLoginException extends RuntimeException{
    //当抛出这个异常的时候，能通过异常携带字符串信息
    public MyLoginException(String message) {
        super(message);
    }
}
