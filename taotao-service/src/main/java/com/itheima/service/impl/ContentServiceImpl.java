package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.itheima.mapper.ContentMapper;
import com.itheima.pojo.Content;
import com.itheima.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;

import java.util.*;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service.impl
 *  @文件名:   ContentServiceImpl
 *  @创建者:   xiaomi
 *  @创建时间:  2018/9/26 19:04
 *  @描述：    TODO
 */

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    @Override
    public int add(Content content) {
        Date date = new Date();
        content.setCreated(date);
        content.setUpdated(date);

        int result= contentMapper.insert(content);
        //更新mysql完毕之后， 也要去更新redis的数据
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("bingAd","");

        return result;
    }

    @Override
    public PageInfo<Content> list(Long categoryId, int page, int rows) {
        PageHelper.startPage(page , rows);
        Content content=new Content();
        content.setCategoryId(categoryId);
        List<Content> list = contentMapper.select(content);

        return new PageInfo<Content>(list);
    }

    @Override
    public int update(Content content) {
        Date date = new Date();
        content.setUpdated(date);

      int result=contentMapper.updateByPrimaryKeySelective(content);
        //更新mysql完毕之后， 也要去更新redis的数据
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("bingAd","");

      return  result;

    }

    @Override
    public int delete(String ids) {
        String[] idArray = ids.split(",");
        int result = 0 ;
        for(String id : idArray){

            result += contentMapper.deleteByPrimaryKey(Long.parseLong(id));
        }
        //删除mysql完毕之后， 也要去删除redis的数据
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("bingAd","");
        return result;
    }

    @Override
    public String selectByCategoryId(long cid) {

        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String json = operations.get("bingAd");
        System.out.println("从缓存里面获取广告数据:" + json);
        if(!StringUtils.isEmpty(json)){

            System.out.println("缓存里面有广告的数据，直接返回");
            return json;

        }

        System.out.println("缓存里面没有有广告的数据，要去查询数据库。");
        Content cont=new Content();
        cont.setCategoryId(cid);
        List<Content> contents = contentMapper.select(cont);

        List<Map<String , Object>> list = new ArrayList<>();
        //把从数据库查询出来的集合，遍历，一个content就对应一个map集合
        for(Content content :contents){
            Map<String , Object> map = new HashMap<>();
            map.put("src" , content.getPic());
            map.put("width",670);
            map.put("height" , 240);
            map.put("href",content.getUrl());

            list.add(map);
        }

        //把这个list集合变成json字符串然后存进去。
        json = new Gson().toJson(list);

        //存到redis里面去。
        operations.set( "bingAd", json);

        System.out.println("从mysql查询出来的数据要存到redis数据库去");

        return json;
    }


}
