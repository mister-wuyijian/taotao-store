package com.itheima.controller;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.controller
 *  @文件名:   PageController
 *  @创建者:   Administrator
 *  @创建时间:  2018/9/18 11:19
 *  @描述：    TODO
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/rest/page/{pageName}")
   public String page(@PathVariable String pageName){
       return pageName;
   }

    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
