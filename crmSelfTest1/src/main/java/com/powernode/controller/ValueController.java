package com.powernode.controller;

import com.powernode.beans.Type;
import com.powernode.beans.Value;
import com.powernode.service.TypeService;
import com.powernode.service.ValueService;

import com.powernode.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/value")
public class ValueController {

    @Autowired
    ValueService valueService;
    @Autowired
    TypeService typeService;
    //显示字典值页面，并查询出所有选项
    @RequestMapping("indexView")
    public String indexView(Model model){
        List<Value> all = valueService.getAll();
        model.addAttribute("vList",all);
        return "/settings/dictionary/value/index";
    }


    //跳转到增加字典值的页面saveView
    //因为增加的时候，有typeCode下拉选项，需要查询出来
    @RequestMapping("saveView")
    public String saveView(Model model){
        List<Type> all = typeService.getAll();
        model.addAttribute("tList",all);
        return "/settings/dictionary/value/save";
    }


    //执行增加操作save.do
    @RequestMapping("save.do")
    public String saveDo(Value value,String typeCode){
        //难不成还要设置value的id吗？因为主键不会自动增长，并且添加页面无法添加主键id
        value.setId(UUIDUtils.getUUID());
        //并且表单提交的typeCode就是type类提供的String类型的code，value中的type属性能接收到这个String类型的typeCode吗？
        //尝试了一下，前端给的typeCode就是type类的String类型的code，并不能被value表中的Type类型的type接收

        //所以通过形式参数先接收前端的 String typeCode，添加进一个Type类中，然后重新加入Value中
        Type type = new Type();
        type.setCode(typeCode);
        value.setType(type);
        valueService.save(value);
        return "redirect:/value/indexView";
    }


    //跳转到修改页面，并回显数据editView
    //通过选中的id，查询出数据，然后显示
    @RequestMapping("editView")
    public String editView(String id,Model model){
        List<Type> all = typeService.getAll();
        model.addAttribute("tList",all);

        Value byId = valueService.getById(id);
        model.addAttribute("value",byId);
        return "/settings/dictionary/value/edit";
    }


    //修改操作执行update.do
    @RequestMapping("update.do")
    public String updateDo(Value value,String typeCode){
        Type type = new Type();
        type.setCode(typeCode);
        value.setType(type);
        valueService.edit(value);
        return "redirect:/value/indexView";
    }


    //执行删除操作delete.do
    @RequestMapping("delete.do")
    public String deleteDo(String[] ids){
        valueService.delete(ids);
        return "redirect:/value/indexView";
    }

}
