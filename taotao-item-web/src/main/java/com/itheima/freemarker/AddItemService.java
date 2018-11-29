package com.itheima.freemarker;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Item;
import com.itheima.pojo.ItemDesc;
import com.itheima.service.ItemDescService;
import com.itheima.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.service
 *  @文件名:   AddItemService
 *  @创建者:   Administrator
 *  @创建时间:  2018/11/27 14:07
 *  @描述：    TODO
 */
@Component
public class AddItemService {
    @Reference
    private ItemService itemService;
    @Reference
    private ItemDescService itemDescService;

    @JmsListener(destination = "item_save")
    public  void addItem(String message)throws  Exception{
        System.out.println("收到的消息是:" + message);
        long id=Long.parseLong(message);
        Item item=itemService.getItemById(id);
        ItemDesc itemDesc = itemDescService.getItemDescById(Long.parseLong(message));

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);

        String  path = "C:\\Users\\Administrator\\IdeaProjects\\taotao-store\\taotao-item-web\\src\\main\\webapp\\ftl";
        configuration.setDirectoryForTemplateLoading(new File(path));

        Template template = configuration.getTemplate("item.ftl");

        Writer out  = new FileWriter("D:\\taotao\\html\\"+message+".html");

        Map<String , Object> root = new HashMap<>();
        root.put("item" , item);
        root.put("itemDesc" , itemDesc);

        template.process(root ,out);

        out.close();


    }
}
