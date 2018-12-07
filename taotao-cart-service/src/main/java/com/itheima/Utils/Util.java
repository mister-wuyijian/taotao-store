package com.itheima.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.pojo.Cart;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.Utils
 *  @文件名:   Util
 *  @创建者:   Administrator
 *  @创建时间:  2018/12/4 14:43
 *  @描述：    TODO
 */
public class Util {
   public static List<Cart> getCartList(RedisTemplate<String ,String> redisTemplate,String key){
       String json=redisTemplate.opsForValue().get(key);
       List<Cart> cartList=new Gson().fromJson(json,  new TypeToken<List<Cart>>(){}.getType());
       return cartList;
   }
   public static void saveCartlist(List<Cart> cartList,RedisTemplate<String ,String> redisTemplate,String key){
       String json = new Gson().toJson(cartList);
       redisTemplate.opsForValue().set(key , json);
   }

}
