package com.itheima.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.pojo.Cart;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.service.utilMethod
 *  @文件名:   getCartList
 *  @创建者:   Administrator
 *  @创建时间:  2018/12/5 19:42
 *  @描述：    TODO
 */
public class getCartList {
    private  final static String CART_KEY_COOKIE="iit_cart";
    private  final static String CART_KEY_REDIS="iitcart_";
    public static List<Cart> getCartListFromCookie(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        List<Cart> CookieCartList=null;
        try {
            for (Cookie cookie : cookies) {
                if(CART_KEY_COOKIE.equals(cookie.getName())){
                    String json=cookie.getValue();
                    json= URLDecoder.decode(json,"utf-8");
                    CookieCartList=new Gson().fromJson(json,  new TypeToken<List<Cart>>(){}.getType());
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(CookieCartList==null){
            CookieCartList=new ArrayList<>();
        }
        return CookieCartList;
    }

    public static List<Cart> getCartListFromRedis(RedisTemplate<String ,String> redisTemplate, long userId){
        System.out.println("要获取redis购物车了");
        List<Cart> RedisCartList=null;
        String json=redisTemplate.opsForValue().get(CART_KEY_REDIS+userId);
        System.out.println("json=" + json);
        RedisCartList=new Gson().fromJson(json,  new TypeToken<List<Cart>>(){}.getType());
        if(RedisCartList==null){
            RedisCartList=new ArrayList<>();
        }
        return RedisCartList;
    }

    public static void saveCartlistToRedis(List<Cart> cartList,RedisTemplate<String ,String> redisTemplate,long userId){
        String json = new Gson().toJson(cartList);
        redisTemplate.opsForValue().set(CART_KEY_REDIS+userId , json);
    }
}
