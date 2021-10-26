package com.powernode.utils;

import java.util.UUID;

public class UUIDUtils {
    // 返回32位随机字符串
    public static String getUUID() {
        System.out.println("使用了UUID这个工具类");
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void test1(){
        System.out.println("随意添加的第一个方法，为了测试IDEA中进行git的提交");
    }
}
