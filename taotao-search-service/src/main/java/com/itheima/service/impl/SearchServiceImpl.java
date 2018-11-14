package com.itheima.service.impl;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.service.impl
 *  @文件名:   SearchServiceImpl
 *  @创建者:   Administrator
 *  @创建时间:  2018/11/14 9:58
 *  @描述：    TODO
 */

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.pojo.Item;
import com.itheima.pojo.Page;
import com.itheima.service.SearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SolrClient solrClient;

    @Override
    public Page<Item> searchItem(String q, int page) {
        try {

            SolrQuery query = new SolrQuery();
            query.setQuery("item_title:"+q);
            query.setStart((page - 1 ) * 16);
            query.setRows(16);
            QueryResponse response = solrClient.query(query);
            SolrDocumentList results = response.getResults();

            long total = results.getNumFound();

            System.out.println("total=" + total);

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
