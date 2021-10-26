package com.powernode;



import com.powernode.beans.User;
import com.powernode.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//设置Spring测试启动类
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({ "classpath:applicationContext.xml"})
public class UserLoginTest1 {

    @Autowired
    UserService userService;

    //测试业务需要是否编写完毕
    @Test
    public void test1(){
        //当账号或密码错误
        //当账号锁定
        //当账号失效时间过了
        //当账号请求地址ip不在范围内
        User login = userService.login("1000", "123", "127.0.0.1");
        System.out.println(login);
    }

}
