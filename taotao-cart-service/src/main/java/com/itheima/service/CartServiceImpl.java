package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.mapper.ItemMapper;
import com.itheima.pojo.Cart;
import com.itheima.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.service
 *  @文件名:   CartServiceImpl
 *  @创建者:   Administrator
 *  @创建时间:  2018/11/28 15:39
 *  @描述：    TODO
 */
@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void addItemToCart(long userId, long id, int num) {
        System.out.println("要添加商品到购物车了");

        List<Cart> cartList= queryCartByUserId(userId);

        Cart c = null;

        //假如购物车中有10件商品
        for (Cart cart : cartList) {
            //购物车里面有这件商品
            if(cart.getItemId() == id){
                c = cart;
                break;
            }
        }

        //判断是否有一样的商品
        if(c != null){
            //有这个商品
            c.setNum(c.getNum() + num );
            c.setUpdate(new Date());
        }else{
            //没有这个商品  --- 去查询数据库，获取这个商品，然后追加到list集合中
            Item item = itemMapper.selectByPrimaryKey(id);

            Cart cart = new Cart();
            cart.setItemId(id);
            cart.setItemTitle(item.getTitle());
            cart.setItemImage(item.getImages()[0]);
            cart.setItemPrice(item.getPrice());
            cart.setCreate(new Date());
            cart.setUpdate(new Date());
            cart.setUserId(userId);
            cart.setNum(num);

            cartList.add(cart);
        }


        //把这个list集合保存到redis中。
        String json = new Gson().toJson(cartList);

        System.out.println("现在购物车的商品有:" + json);

        redisTemplate.opsForValue().set("iitcart_"+userId , json);

    }

    @Override
    public List<Cart> queryCartByUserId(long userId) {
        String json=redisTemplate.opsForValue().get("iitcart_"+userId);

        if(!StringUtils.isEmpty(json)){
            //2. 把json字符串转化成list<Cart>
            List<Cart> cartList = new Gson().fromJson(json,  new TypeToken<List<Cart>>(){}.getType());

            return cartList;
        }

        //如果是空，表示是第一次来添加商品到购物车，所以redis里面不会有任何数据
        return new ArrayList<Cart>();
    }
}
