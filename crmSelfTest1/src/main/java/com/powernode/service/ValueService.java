package com.powernode.service;

import com.powernode.beans.Value;
import com.powernode.mapper.BaseMapper;

import java.util.List;

public interface ValueService{
    List<Value> getAll();
    int save(Value value);
    Value getById(String id);
    int edit(Value value);
    int delete(String[] ids);
        /*
    List<T> getAll();
    T getById(I id);
    int save(T type);
    int edit(T type);
    int delete(I[] ids);
    * */
}
