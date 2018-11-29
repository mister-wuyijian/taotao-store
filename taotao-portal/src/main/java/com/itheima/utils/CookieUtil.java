package com.itheima.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.utils
 *  @文件名:   CookieUtil
 *  @创建者:   xiaomi
 *  @创建时间:  2018/11/28 18:52
 *  @描述：    TODO
 */
public class CookieUtil {

    public static String findTicket(HttpServletRequest request){

        String ticket = null;

        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if("ticket".equals(name)){
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        return ticket;
    }
}
