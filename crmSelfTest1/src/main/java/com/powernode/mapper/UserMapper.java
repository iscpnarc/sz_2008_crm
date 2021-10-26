package com.powernode.mapper;

import com.powernode.beans.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    //因为是验证登录，只需要查询一个对象，限定了条件loginAct=？？  loginPwd=？？
    //所以只需要返回User对象，而不是User对象的集合
    //但是，查询验证登录，需要输入账号和密码，这是前端会传过来的数据，也是后端的查询条件
    //实际参数的名称和占位符需要的名称可能不一致，所以需要注解 @Param("loginAct") 来表示替换占位符
    User getUser(@Param("loginAct") String loginAct,@Param("loginPwd") String loginPwd );

    List<String> getUserString();
}
