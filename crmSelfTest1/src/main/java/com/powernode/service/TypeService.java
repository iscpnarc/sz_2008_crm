package com.powernode.service;

import com.powernode.beans.Type;

import java.util.List;

public interface TypeService {
    List<Type> getAll();
    Type getById(String id);
    int save(Type type);
    int edit(Type type);
    int delete(String[] ids);
    /*
    List<T> getAll();
    T getById(I id);
    int save(T type);
    int edit(T type);
    int delete(I[] ids);
    */
}
