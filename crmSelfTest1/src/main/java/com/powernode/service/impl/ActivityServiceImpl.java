package com.powernode.service.impl;

import com.powernode.beans.Activity;
import com.powernode.beans.Page;
import com.powernode.mapper.ActivityMapper;
import com.powernode.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityMapper activityMapper;

    @Override
    public List<Activity> getAll(Page page) {
        //Page分页类不仅仅是需要data数据显示，还要计算出总记录数totalRows和总页数totalPages
        int count = activityMapper.getCount();
        //设置总记录数
        page.setTotalPages(count);
        //每页的记录数
        Integer rowsPerPage = page.getRowsPerPage();
        if(count%rowsPerPage==0){
            //计算出总页数并设置
            page.setTotalPages(count%rowsPerPage);
        }else{
            page.setTotalPages( (count%rowsPerPage) + 1 );
        }

        //分页查询需要的两个数据，从第几条数据开始查询，查询多少条数据
        int st=(page.getCurrentPage()-1) * page.getRowsPerPage();
        int rp=page.getRowsPerPage();
        return activityMapper.getAll(st,rp);
    }

    @Override
    public int getCount() {
        return activityMapper.getCount();
    }
}
