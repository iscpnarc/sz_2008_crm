package com.powernode.mapper;

import com.powernode.beans.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityMapper extends BaseMapper<Activity,String>{

    List<Activity> getAll(@Param("st") int st,@Param("rp") int rp);
    int getCount();

}
