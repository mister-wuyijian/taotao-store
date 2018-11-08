package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima
 *  @文件名:   ManagerApp
 *  @创建者:   Administrator
 *  @创建时间:  2018/9/11 21:15
 *  @描述：    TODO
 */


//告诉Springboot不要检测数据源
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)

@SpringBootApplication
public class ManagerApp {
   public static void main(String [] args){
       SpringApplication.run(ManagerApp.class,args);
   }
}
