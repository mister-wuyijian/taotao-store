package com.itheima.service.orderUntil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.service.orderUntil
 *  @文件名:   redisUntil
 *  @创建者:   Administrator
 *  @创建时间:  2018/12/15 14:03
 *  @描述：    TODO
 */
public class redisUntil {
    @Autowired
    private RedisTemplate<String,String> template;

    public static String getOrderId(RedisTemplate<String ,String> template,String key){
        return  ""+template.opsForValue().increment(key,1);
    }
}
