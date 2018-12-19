package com.itheima.listener;

import com.itheima.quartz.orderScheduler;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.listener
 *  @文件名:   orderListener
 *  @创建者:   Administrator
 *  @创建时间:  2018/12/19 23:03
 *  @描述：    TODO
 */
@Component
public class orderListener implements ApplicationListener<ApplicationReadyEvent> {
    @Resource(name = "od")
    private orderScheduler scheduler;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        try {
            scheduler.startJob();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    @Override
//    public void contextInitialized(ServletContextEvent event) {
//        try {
//            scheduler.startJob();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent event) {
//        try {
//            scheduler.pauseJob();
//            scheduler.deleteJob();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
