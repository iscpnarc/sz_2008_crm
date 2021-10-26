package com.powernode.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 分页对象
 * 需要在后台计算的属性有：
 *  1. 总记录数，select count(*) from xxx
 *  2. 总页数，需要根据总记录数和每页条数进行计算
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page implements Serializable {
    //分页参数
    private Integer currentPage = 1;        // 当前页（查询条件）
    private Integer rowsPerPage = 3;       // 每页显示的记录条数（查询条件）
    private Integer maxRowsPerPage = 100;   // 每页最多显示的记录条数(配置)
    private Integer totalPages;             // 总页数【必须计算出来】
    private Integer totalRows;              // 总记录数【必须计算出来】
    private Integer visiblePageLinks = 5;   // 显示几个卡片(配置)


    //分页数据集
    private List data;//翻页数据

    //查询集合
    private Map<String, Object> searchMap; // 查询条件

}
