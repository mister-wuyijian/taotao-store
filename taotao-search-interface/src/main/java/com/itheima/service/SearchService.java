package com.itheima.service;

import com.itheima.pojo.Item;
import com.itheima.pojo.Page;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.service
 *  @文件名:   SearchService
 *  @创建者:   Administrator
 *  @创建时间:  2018/11/14 9:52
 *  @描述：    TODO
 */
public interface SearchService {
    Page<Item> searchItem(String q,int page);
}
