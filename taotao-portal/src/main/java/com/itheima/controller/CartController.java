package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.noLogin.cartCookieService;
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
import javax.servlet.http.HttpServletResponse;
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
    private cartCookieService cartCookieService;
    @Autowired
    private RedisTemplate<String,String> template;

    @RequestMapping("cart/add/{id}.html")
   public String AddToCart(@PathVariable long id, int num, HttpServletRequest request, HttpServletResponse response){

        String ticket = CookieUtil.findTicket(request);

        if(ticket != null){

            User user = RedisUtil.findUserByTicket(template, ticket);

            cartService.addItemToCart(user.getId() , id , num);
        }else{
            cartCookieService.addItemByCookie(id,num,request,response);
        }


        return "cartSuccess";
   }
    @RequestMapping("/cart/cart.html")
    public String showCart(HttpServletRequest request , Model model){

        String ticket = CookieUtil.findTicket(request);
        List<Cart> cartList;

        if(ticket != null){

            User user = RedisUtil.findUserByTicket(template, ticket);
            cartList = cartService.queryCartByUserId(user.getId());

        }else{
                cartList=cartCookieService.queryCartByCookie(request);
        }
        model.addAttribute("cartList" , cartList);
        return "cart";
    }

    @RequestMapping("/service/cart/update/num/{id}/{num}")
    public void updateNumByCart(@PathVariable long id,@PathVariable int num,HttpServletRequest request,HttpServletResponse response){
        String ticket = CookieUtil.findTicket(request);

        if(ticket != null){

            User user = RedisUtil.findUserByTicket(template, ticket);
            cartService.updateItemFromCart(user.getId(),id,num);

        }else{
          cartCookieService.updateCartByCookie(request,response,id,num);

        }
    }

    @RequestMapping("/cart/delete/{id}.shtml")
    public String deleteItemByCart(@PathVariable long id,HttpServletRequest request,HttpServletResponse response){

        String ticket = CookieUtil.findTicket(request);
        if(ticket!=null){
            User user = RedisUtil.findUserByTicket(template, ticket);
            cartService.deleteItemFromCart(user.getId(),id);
        }else {
            cartCookieService.deleteCartByCookie(request,response,id);
        }

        return "redirect:/cart/cart.html";

        }


}
