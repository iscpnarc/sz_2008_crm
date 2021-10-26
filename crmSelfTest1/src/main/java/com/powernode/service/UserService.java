package com.powernode.service;

import com.powernode.beans.User;

import java.util.List;

public interface UserService {

    //这里负责登录时的业务需求
    /*
    1、状态是否锁定，0表示锁定，登录不会成功
    2、是否在失效时间之内，否则登录不会成功
    3、账户是否为空？也就是是否账号密码出错
    4、请求地址ip是否在账号的ip允许地址范围内，不在，登录不成功
    */

    User login(String loginAct,String loginPwd ,String ip);
    List<String> getUserString();
}
