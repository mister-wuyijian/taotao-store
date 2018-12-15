package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Cart;
import com.itheima.pojo.Order;
import com.itheima.pojo.User;
import com.itheima.service.CartService;
import com.itheima.service.OrderService;
import com.itheima.utils.CookieUtil;
import com.itheima.utils.RedisUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Reference
    private OrderService orderService;

    @RequestMapping("/order/order-cart.shtml")
   public String create(HttpServletRequest request, Model model){
        User user= (User) request.getAttribute("user");
        List<Cart> cartList=cartService.queryCartByUserId(user.getId());
        model.addAttribute("carts",cartList);
       return "order-cart";
   }

   @RequestMapping("/service/order/submit")
   @ResponseBody
   public Map<String,String> submitOrder(Order order,HttpServletRequest request){

       String ticket= CookieUtil.findTicket(request);
       User user= RedisUtil.findUserByTicket(template,ticket);


       order.setUserId(000L);
       order.setBuyerNick("");

       String orderId=orderService.saveOrder(order);
       System.out.println("orderId="+orderId);

       Map<String ,String> map=new HashMap<>();
       map.put("status","200");
       map.put("data",orderId);

       System.out.println("order=" + order);

       return  map;
   }

   @RequestMapping("/order/success.html")
   public String showOrder(String id,Model model){

       Order order=orderService.queryOrderByOrderId(id);
       model.addAttribute("order",order);
       String date= new DateTime().plusDays(2).toString("yyyy年MM月dd日HH时mm分ss秒SSS毫秒");
       model.addAttribute("date",date);
       return  "success";
   }

}
