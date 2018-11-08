package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.itheima.pojo.User;
import com.itheima.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.controller
 *  @文件名:   IndexController
 *  @创建者:   xiaomi
 *  @创建时间:  2018/9/25 13:54
 *  @描述：    TODO
 */
@Controller
public class IndexController {
    @Reference
    private ContentService contentService;

    @Autowired
    private RedisTemplate<String ,String> redisTemplate;

    @RequestMapping("/page/{pageName}")
    public String page(@PathVariable String pageName){
        return pageName;
    }

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request){
        System.out.println("要获取首页的广告数据了~");


        //在这里获取ticket， 然后到redis里面去查询用户数据，然后放到页面显示就好了。
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                System.out.println("name=" + name);
                if("ticket".equals(name)){
                    String key = cookie.getValue();
                    String userInfo =redisTemplate.opsForValue().get(key);
                    System.out.println("key="+key);

                    User user = new Gson().fromJson(userInfo , User.class);

                    model.addAttribute("user",user);

                    break;
                }
            }
        }


        int categoryId = 89;
        String json=contentService.selectByCategoryId(categoryId);

        model.addAttribute("list",json);
        return "index";

    }

}
