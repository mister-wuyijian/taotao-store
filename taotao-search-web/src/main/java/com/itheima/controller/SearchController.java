package com.itheima.controller;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.controller
 *  @文件名:   SearchController
 *  @创建者:   xiaomi
 *  @创建时间:  2018/11/13 13:42
 *  @描述：    TODO
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Item;
import com.itheima.pojo.Page;
import com.itheima.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Reference
    private SearchService searchService;



    @RequestMapping("/search")
    public String search(Model model,String q , @RequestParam(defaultValue = "1") int page){

        System.out.println("要跳转到搜索页面了 ==="+q);

        Page<Item> pageItem=searchService.searchItem(q , page);
        model.addAttribute("query",q);
        model.addAttribute("page",pageItem);
        model.addAttribute("totalPages",pageItem.getTotal());
        return "search";
    }
}
