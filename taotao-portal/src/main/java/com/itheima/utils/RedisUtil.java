package com.itheima.utils;

import com.google.gson.Gson;
import com.itheima.pojo.User;
import org.springframework.data.redis.core.RedisTemplate;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.utils
 *  @文件名:   RedisUtil
 *  @创建者:   xiaomi
 *  @创建时间:  2018/11/28 18:54
 *  @描述：    TODO
 */
public class RedisUtil {

    public static User findUserByTicket(RedisTemplate template , String ticket){
        String json = (String) template.opsForValue().get(ticket);
        return new Gson().fromJson(json , User.class);
    }
}
