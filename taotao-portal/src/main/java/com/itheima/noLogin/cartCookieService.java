package com.itheima.noLogin;

import com.itheima.pojo.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.noLogin
 *  @文件名:   cartCookieService
 *  @创建者:   Administrator
 *  @创建时间:  2018/12/4 23:09
 *  @描述：    TODO
 */
public interface cartCookieService {
    void addItemByCookie(long itemId, int num, HttpServletRequest request, HttpServletResponse response);
    List<Cart> queryCartByCookie(HttpServletRequest request);
    void saveCartByCookie(List<Cart> cartList,HttpServletResponse response);
    void updateCartByCookie(HttpServletRequest request,HttpServletResponse response,long itemId,int num);
    void deleteCartByCookie(HttpServletRequest request,HttpServletResponse response,long itemId);
}
