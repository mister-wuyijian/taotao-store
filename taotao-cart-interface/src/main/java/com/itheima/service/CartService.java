package com.itheima.service;

import com.itheima.pojo.Cart;

import java.util.List;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service
 *  @文件名:   CartService
 *  @创建者:   xiaomi
 *  @创建时间:  2018/11/28 11:21
 *  @描述：    TODO
 */
public interface CartService {

    void addItemToCart(long userId, long id, int num);

    List<Cart> queryCartByUserId(long userId);

    void updateItemFromCart(long userId,long itemId,int num);

    void deleteItemFromCart(long userId, long itemId);
}
