package com.itheima.intercepetorConfig;

import com.itheima.intercepetor.OrderIntercepetor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.intercepetorConfig
 *  @文件名:   interConfig
 *  @创建者:   Administrator
 *  @创建时间:  2018/12/11 10:50
 *  @描述：    TODO
 */
@Component
public class interConfig extends WebMvcConfigurerAdapter{
    @Autowired
   private OrderIntercepetor orderIntercepetor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(orderIntercepetor).addPathPatterns("/order/*");
    }
}
