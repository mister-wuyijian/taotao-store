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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.controller
 *  @文件名:   CartController
 *  @创建者:   Administrator
 *  @创建时间:  2018/11/28 11:20
 *  @描述：    TODO
 */
@Controller
public class CartController {

    @Reference
    private CartService cartService;
    @Autowired
    private RedisTemplate<String,String> template;

    @RequestMapping("cart/add/{id}.html")
   public String AddToCart(@PathVariable long id,int num, HttpServletRequest request){



        String ticket = CookieUtil.findTicket(request);

        if(ticket != null){


            User user = RedisUtil.findUserByTicket(template, ticket);


            cartService.addItemToCart(user.getId() , id , num);
        }else{
            //没有登录
        }


        return "cartSuccess";
   }
    @RequestMapping("/cart/cart.html")
    public String showCart(HttpServletRequest request , Model model){

        String ticket = CookieUtil.findTicket(request);

        if(ticket != null){


            User user = RedisUtil.findUserByTicket(template, ticket);

            List<Cart> cartList = cartService.queryCartByUserId(user.getId());

            model.addAttribute("cartList" , cartList);

        }else{

        }


        return "cart";
    }
}
