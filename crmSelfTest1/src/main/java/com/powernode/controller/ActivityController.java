package com.powernode.controller;

import com.powernode.beans.Activity;
import com.powernode.beans.Page;
import com.powernode.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/act")
public class ActivityController {
    @Resource
    ActivityService activityService;

    //展示活动首页，查询出所有活动
    @RequestMapping("indexView")
    public String indexView(){
        return "/workbench/activity/index";
    }


    //getAll.json展示前端页面需要相应ajax请求传回前端数据
    @RequestMapping("getAll.json")
    @ResponseBody
    public Page getAllJSON(Page page){
        //前端ajax请求中数据，当前页currentPage、每页记录数rowsPerPage，通过一个Page类接收
        //ajax请求需要的数据，page.data需要我们填充然后响应,而data数据集是通过查询所有的Activity所得的
        List<Activity> all = activityService.getAll(page);
        //设置数据集data
        page.setData(all);

        return page;
    }
}
