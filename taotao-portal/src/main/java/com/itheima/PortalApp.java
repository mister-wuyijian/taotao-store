package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima
 *  @文件名:   PortalApp
 *  @创建者:   Administrator
 *  @创建时间:  2018/9/25 16:24
 *  @描述：    TODO
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class PortalApp {
    public static void main(String [] args){
        SpringApplication.run(PortalApp.class,args)  ;
    }
}
