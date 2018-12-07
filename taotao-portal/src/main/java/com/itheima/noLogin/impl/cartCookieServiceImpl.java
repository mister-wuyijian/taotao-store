package com.itheima.noLogin.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.noLogin.cartCookieService;
import com.itheima.pojo.Cart;
import com.itheima.pojo.Item;
import com.itheima.service.ItemService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.noLogin
 *  @文件名:   cartCookieServiceImpl
 *  @创建者:   Administrator
 *  @创建时间:  2018/12/4 23:10
 *  @描述：    TODO
 */
@Service
public class cartCookieServiceImpl implements cartCookieService {
    private  final static String CART_KEY="iit_cart";
    @Reference
    private ItemService itemService;

    @Override
    public void addItemByCookie(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
        List<Cart> cartList=queryCartByCookie(request);
        Cart c=null;
        for (Cart cart : cartList) {
            if(cart.getItemId()==itemId){
                c=cart;
                break;
            }
        }
        if(c!=null){
            c.setNum(c.getNum()+num );
            c.setUpdate(new Date());
        }else {
            Item item=itemService.getItemById(itemId);
            Cart cart = new Cart();
            cart.setItemId(itemId);
            cart.setItemTitle(item.getTitle());
            cart.setItemImage(item.getImages()[0]);
            cart.setItemPrice(item.getPrice());
            cart.setCreate(new Date());
            cart.setUpdate(new Date());
            cart.setNum(num);

            cartList.add(cart);
        }
        String json=new Gson().toJson(cartList);
        try {
            json=URLEncoder.encode(json,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Cookie cookie=new Cookie(CART_KEY,json);
        cookie.setMaxAge(60*60*24*3);
        cookie.setPath("/");
        response.addCookie(cookie);
        System.out.println("购物车已经添加到cookie去了~·~~");
    }
     public List<Cart> queryCartByCookie(HttpServletRequest request){
         Cookie[] cookies=request.getCookies();
         List<Cart> cartList=null;
         try {
             for (Cookie cookie : cookies) {
                 if(CART_KEY.equals(cookie.getName())){
                     String json=cookie.getValue();
                     json= URLDecoder.decode(json,"utf-8");
                     cartList=new Gson().fromJson(json,  new TypeToken<List<Cart>>(){}.getType());
                     break;
                 }
             }
         } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
         }
         if(cartList==null){
             cartList=new ArrayList<>();
         }
         return cartList;
    }


    @Override
    public void saveCartByCookie(List<Cart> cartList,HttpServletResponse response) {
        String json = new Gson().toJson(cartList);
        try {
            json=URLEncoder.encode(json,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Cookie cookie=new Cookie(CART_KEY,json);
        cookie.setMaxAge(60*60*24*3);
        cookie.setPath("/");
        response.addCookie(cookie);
        System.out.println("num改变后的购物车已保存进cookie");
    }

    @Override
    public void updateCartByCookie(HttpServletRequest request,HttpServletResponse response,long itemId,int num) {
        List<Cart> cartList=queryCartByCookie(request);
        Iterator iter=cartList.iterator();
        while (iter.hasNext()){
            Cart cart= (Cart) iter.next();
            if(cart.getItemId().equals(itemId)){
                cart.setNum(num);
                cart.setUpdate(new Date());
                break;
            }
        }
        saveCartByCookie(cartList,response);
    }

    @Override
    public void deleteCartByCookie(HttpServletRequest request,HttpServletResponse response,long itemId) {
        List<Cart> cartList=queryCartByCookie(request);
        Iterator iter=cartList.iterator();
        while (iter.hasNext()){
            Cart cart= (Cart) iter.next();
            if(cart.getItemId().equals(itemId)){
               cartList.remove(cart);
                break;
            }
        }
        saveCartByCookie(cartList,response);
    }


}
