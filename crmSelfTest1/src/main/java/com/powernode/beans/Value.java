package com.powernode.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Value {
    String id;
    String value;
    String text;
    int orderNo;
    /*String typeCode;*/
    //value表的最后一个字段typeCode是外键，是type表的主键code。利用ORM使用类进行映射
    Type type;

}
