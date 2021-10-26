package com.powernode.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    String id;
    String owner;
    String name;
    String startDate;
    String endDate;
    int    cost;
    String description;
    String createBy;
    String createTime;
    String editBy;
    String editTime;
}
