package com.powernode.utils;

import java.util.UUID;

public class UUIDUtils {
    // 返回32位随机字符串
    public static String getUUID() {
        System.out.println("使用了UUID这个工具类");
        return UUID.randomUUID().toString().replace("-", "");
    }
}
