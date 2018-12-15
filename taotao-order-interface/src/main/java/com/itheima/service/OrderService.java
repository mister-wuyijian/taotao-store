package com.itheima.service;

import com.itheima.pojo.Order;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.service
 *  @文件名:   OrderService
 *  @创建者:   Administrator
 *  @创建时间:  2018/12/15 12:51
 *  @描述：    TODO
 */
public interface OrderService {
    String saveOrder(Order order);
    Order queryOrderByOrderId(String orderId);
}
