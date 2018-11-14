package com.itheima.controller;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.controller
 *  @文件名:   PageController
 *  @创建者:   xiaomi
 *  @创建时间:  2018/11/7 18:51
 *  @描述：    TODO
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/page/search.shtml")
    public String page(){
        System.out.println("要跳转到搜索的页面了");
        return "search";
    }

}
