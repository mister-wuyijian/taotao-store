package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.OrderItemMapper;
import com.itheima.mapper.OrderMapper;
import com.itheima.mapper.OrderShippingMapper;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderItem;
import com.itheima.pojo.OrderShipping;
import com.itheima.service.OrderService;
import com.itheima.service.orderUntil.redisUntil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Random;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.service.impl
 *  @文件名:   OrderServiceImpl
 *  @创建者:   Administrator
 *  @创建时间:  2018/12/15 12:52
 *  @描述：    TODO
 */
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderShippingMapper orderShippingMapper;
    @Autowired
    private RedisTemplate<String,String> template;

    @Override
    public String saveOrder(Order order) {
        String orderId= redisUntil.getOrderId(template,"order_"+order.getUserId());
        order.setOrderId(orderId);
        order.setStatus(1);
        order.setCreateTime(new Date());
        order.setUpdateTime(order.getCreateTime());
        orderMapper.insertSelective(order);

        List<OrderItem> items=order.getOrderItems();
        for (OrderItem item : items) {
            item.setOrderId(orderId);
            item.setId(new Random().nextInt(10000)+"");
            orderItemMapper.insertSelective(item);
        }

        OrderShipping orderShipping=order.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(orderShipping.getCreated());
        orderShippingMapper.insertSelective(orderShipping);


        return orderId;
    }
    @RequestMapping("/order/success.html?id={orderId}")
    public Order queryOrderByOrderId(@PathVariable String orderId){

        Order order=orderMapper.selectByPrimaryKey(orderId);

        OrderItem item=new OrderItem();
        item.setOrderId(orderId);
        List<OrderItem> items=orderItemMapper.select(item);
        order.setOrderItems(items);

        OrderShipping shipping=orderShippingMapper.selectByPrimaryKey(orderId);
        order.setOrderShipping(shipping);

        return  order;
    }

    @Override
    public void clearOrder() {
        Order order=new Order();
        order.setStatus(6);
        order.setCloseTime(new Date());

        Example example=new Example(Order.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("status",1);
        criteria.andEqualTo("paymentType",1);
        criteria.andLessThan("createTime",new DateTime().minusDays(2).toDate());

        orderMapper.updateByExampleSelective(order,example);
    }

}
