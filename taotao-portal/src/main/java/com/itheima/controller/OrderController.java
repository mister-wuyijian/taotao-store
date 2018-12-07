package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Cart;
import com.itheima.pojo.User;
import com.itheima.service.CartService;
import com.itheima.utils.CookieUtil;
import com.itheima.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.controller
 *  @文件名:   OrderController
 *  @创建者:   Administrator
 *  @创建时间:  2018/12/7 18:12
 *  @描述：    TODO
 */
@Controller
public class OrderController {
    @Autowired
    private RedisTemplate<String ,String> template;
    @Reference
    private CartService cartService;

    @RequestMapping("/order/order-cart.shtml")
   public String create(HttpServletRequest request, Model model){
        String ticket= CookieUtil.findTicket(request);
        User user= RedisUtil.findUserByTicket(template,ticket);
        List<Cart> cartList=cartService.queryCartByUserId(user.getId());
        model.addAttribute("carts",cartList);
       return "order-cart";
   }

}
