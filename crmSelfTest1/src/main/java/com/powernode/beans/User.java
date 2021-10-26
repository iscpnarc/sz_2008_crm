package com.powernode.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String id;
    String deptId;
    String loginAct;
    String name;
    String loginPwd;
    String email;
    String expireTime;
    String lockStatus;
    String allowIps;
    String createBy;
    String createTime;
    String editBy;
    String editTime;

}
