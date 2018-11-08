package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.mapper.ItemDescMapper;
import com.itheima.mapper.ItemMapper;
import com.itheima.pojo.Item;
import com.itheima.pojo.ItemDesc;
import com.itheima.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service.impl
 *  @文件名:   ItemServiceImpl
 *  @创建者:   Administrator
 *  @创建时间:  2018/9/19 9:38
 *  @描述：    TODO
 */

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescMapper itemDescMapper;

    @Override
    public int addItem(Item item, String desc) {
        long id = (long) (System.currentTimeMillis() + Math.random() * 10000);
        item.setId(id);
        item.setStatus(1);
        item.setCreated(new Date());
        item.setUpdated(new Date());

        int result = itemMapper.insertSelective(item);

        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(id);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());


        itemDescMapper.insertSelective(itemDesc);

        return result;
    }

    @Override
    public PageInfo<Item> list(int page, int rows) {
        PageHelper.startPage(page , rows);

        List<Item> list = itemMapper.select(null);

        return new PageInfo<Item>(list);
    }
}
