package com.itheima.controller;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.controller
 *  @文件名:   ContentController
 *  @创建者:   xiaomi
 *  @创建时间:  2018/9/26 19:02
 *  @描述：    TODO
 */


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.pojo.Content;
import com.itheima.service.ContentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ContentController {

    @Reference
    private ContentService contentService;

    @RequestMapping("/rest/content")
    public String add(Content content){
        contentService.add(content);

        return "success";
    }

    @RequestMapping(value = "/rest/content/select" , method = RequestMethod.GET)
    @ResponseBody
    public Map<String  , Object> list(Long categoryId,int page , int rows){

        PageInfo<Content> pageInfo = contentService.list(categoryId,page, rows);
        Map<String  , Object> map = new HashMap<>();
        map.put("total" , pageInfo.getTotal());
        map.put("rows" , pageInfo.getList());
        return map;
    }

    @RequestMapping(value = "/rest/content/edit" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Integer> edit(Content content){
        int result=contentService.update(content);
        Map<String ,Integer> map = new HashMap<>();
        //表示更新成功
        if(result > 0 ){
            map.put("status",200);
        }else{
            map.put("status" , 500);
        }

        return map;
    }

    @RequestMapping(value = "/rest/content/delete" , method = RequestMethod.POST)
    public Map<String ,Integer> delete(String ids){
        System.out.println(ids);
        int result=contentService.delete(ids);

        System.out.println("result=" + result);

        Map<String ,Integer> map = new HashMap<>();
        //表示更新成功
        if(result > 0 ){
            map.put("status",200);
        }else{
            map.put("status" , 500);
        }

        return map;
    }

}
