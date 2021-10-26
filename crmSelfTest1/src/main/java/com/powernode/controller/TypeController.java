package com.powernode.controller;

import com.powernode.beans.Type;
import com.powernode.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/type")
public class TypeController {

    @Autowired
    TypeService typeService;

    //转到字典值的首页，查询出所有数据并显示
    @RequestMapping("indexView")
    public String indexView(Model model){
        List<Type> all = typeService.getAll();
        model.addAttribute("tList",all);
        return "settings/dictionary/type/index";
    }


    //通过控制器跳转到增加字典类型页面saveView
    @RequestMapping("saveView")
    public String saveView(){
        return "settings/dictionary/type/save";
    }

    //增加字典类型的时候，前端发送ajax请求，检查主键code是否重复checkCode.do
    @RequestMapping("checkCode.do")
    @ResponseBody
    public boolean checkCodeDo(String code){
        Type byId = typeService.getById(code);

        return byId==null?false:true;
    }


    //执行增加操作save.do
    //增加成功后，重新跳转首页进行显示
    @RequestMapping("save.do")
    public String saveDo(Type type){
        typeService.save(type);
        return "redirect:/type/indexView";
    }


    //跳转到编辑页面editView,并且将要编辑的内容显示出来
    @RequestMapping("editView")
    public String editView(String code,Model model){
        Type byId = typeService.getById(code);
        model.addAttribute("type",byId);
        return "settings/dictionary/type/edit";
    }

    //执行编辑操作edit.do
    @RequestMapping("edit.do")
    public String editDo(Type type){
        typeService.edit(type);
        return "redirect:/type/indexView";
    }


    //执行删除操作delete.do
    @RequestMapping("delete.do")
    public String deleteDo(String[] ids){
        typeService.delete(ids);
        return "redirect:/type/indexView";
    }
}
