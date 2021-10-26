package com.powernode.mapper;

import java.util.List;

public interface BaseMapper<T,I>  {
    //所有的增删改查大多就五个功能
    List<T> getAll();
    T getById(I id);
    int save(T type);
    int edit(T type);
    int delete(I[] ids);
}
