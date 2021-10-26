package com.powernode.service.impl;

import com.powernode.beans.User;
import com.powernode.exception.MyLoginException;
import com.powernode.mapper.UserMapper;
import com.powernode.service.UserService;
import com.sun.org.apache.xerces.internal.xs.ItemPSVI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    //业务层的实现类，完成业务的需求就行了
    //方法的参数，就是前端的账号密码
    public User login(String loginAct, String loginPwd, String ip) {
        //业务层调用dao层获取查询的User对象
        User user = userMapper.getUser(loginAct, loginPwd);

        //todo 1、账户是否为空？也就是是否账号密码出错
        if(user==null){
            /*System.out.println("账号或者密码错误！登录失败");
            return null;*/
            throw new MyLoginException("账号或者密码错误！登录失败");
        }

        //todo 2、状态是否锁定，0表示锁定，登录不会成功
        String lockStatus = user.getLockStatus();
        if("0".equals(lockStatus)){
            /*System.out.println("账号状态被锁定！登录失败");
            return null;*/
            throw new MyLoginException("账号状态被锁定！登录失败");
        }

        //todo 3、是否在失效时间之内，否则登录不会成功
        //使用jdk8的新特性LocalDateTime
        //比较当前时间和账号失效时间，观察账号是否失效
        LocalDateTime nowTime = LocalDateTime.now();//当前时间

        //规定的时间格式，通过时间格式和LocalDateTime将User对象的字符串格式时间转换成规定的时间格式，好进行比较
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String expireTime = user.getExpireTime();
        LocalDateTime actTime = LocalDateTime.parse(expireTime, dateTimeFormatter);//账号实际失效时间

        if(nowTime.isAfter(actTime)){
            /*System.out.println("账号已经失效了，登录失败");
            return null;*/
            throw new MyLoginException("账号已经失效了，登录失败");
        }

        //todo 4、请求地址ip是否在账号的ip允许地址范围内，不在，登录不成功
        //获取User对象的请求允许的ip地址，因为是字符串形式逗号隔开，所以进行切割放进数组，得到多个允许的ip
        String allowIps = user.getAllowIps();
        String[] IPs = allowIps.split(",");
        List<String> IPList = Arrays.asList(IPs);

        //到这里突然发现，没有获取到请求地址的ip，并且扎起User对象中也不会有
        //但是请求地址的ip不能在模型层中获取，能在控制器层Controller中获取，这里直接将ip地址作为参数传递
        if(!IPList.contains(ip)){
            //如果账号允许请求的ip地址范围，不包括实际请求的ip地址，登录失败
           /* System.out.println("ip地址不在范围内，登录失败");
            return null;*/
            throw new MyLoginException("ip地址不在范围内，登录失败");
        }


        //登陆成功
        //System.out.println("登录成功！");
        return user;
    }

    @Override
    public List<String> getUserString() {
        return userMapper.getUserString();
    }
}
