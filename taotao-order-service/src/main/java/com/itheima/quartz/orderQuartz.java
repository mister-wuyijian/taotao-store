package com.itheima.quartz;

import com.itheima.service.OrderService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.quartz
 *  @文件名:   orderQuartz
 *  @创建者:   Administrator
 *  @创建时间:  2018/12/19 22:27
 *  @描述：    TODO
 */
@Component
public class orderQuartz {
    @Autowired
    private OrderService orderService;
    @Bean
    public JobDetail orderJob(){

        JobDataMap data=new JobDataMap();
        data.put("os",orderService);
        return   JobBuilder
                .newJob(orderJob.class)
                .setJobData(data)
                .withIdentity("clearOrder","order")
                .build();
    }
    @Bean
    public Trigger orderTrigger(){
        ScheduleBuilder builder= CronScheduleBuilder.cronSchedule("*/5 * * * * ?");
        return TriggerBuilder
                .newTrigger()
                .withSchedule(builder)
                .startNow()
                .build();
    }
    @Bean
    public Scheduler orderScheduler() throws  Exception{

        return StdSchedulerFactory.getDefaultScheduler();

    }
}
