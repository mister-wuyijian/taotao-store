package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Item;
import com.itheima.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.controller
 *  @文件名:   ItemController
 *  @创建者:   Administrator
 *  @创建时间:  2018/11/26 14:56
 *  @描述：    TODO
 */
@Controller
public class ItemController {
    @Reference
    private ItemService itemService;

    @RequestMapping("/item/{id}")
    public String item(@PathVariable long id,Model model){
        Item item=itemService.getItemById(id);
        model.addAttribute("item",item);
        return "item";
    }
}
