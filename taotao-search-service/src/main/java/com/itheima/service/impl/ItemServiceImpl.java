package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.ItemMapper;
import com.itheima.pojo.Item;
import com.itheima.service.ItemService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;

import java.io.IOException;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.service.impl
 *  @文件名:   ItemServiceImpl
 *  @创建者:   Administrator
 *  @创建时间:  2018/11/21 9:37
 *  @描述：    TODO
 */
@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private SolrClient solrClient;

    @Autowired
    private ItemMapper itemMapper;

    @JmsListener(destination = "item_save")
    @Override
    public void addSave(String message) {
        long id=Long.parseLong(message);
        System.out.println("收到的消息是：" + id);
        Item item=itemMapper.selectByPrimaryKey(id);
        SolrInputDocument doc=new SolrInputDocument();
        doc.addField("id",id);
        doc.addField("item_title",item.getTitle());
        doc.addField("item_price",item.getPrice());
        doc.addField("item_image",item.getImage());
        doc.addField("item_cid",item.getCid());
        doc.addField("item_status",item.getStatus());
        try {
            solrClient.add(doc);
            solrClient.commit();
            System.out.println("索引库更新了");
            solrClient.close();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
