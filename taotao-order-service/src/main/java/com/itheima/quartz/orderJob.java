package com.itheima.quartz;

import com.itheima.service.OrderService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.quartz
 *  @文件名:   orderJob
 *  @创建者:   Administrator
 *  @创建时间:  2018/12/19 22:23
 *  @描述：    TODO
 */
@Component
public class orderJob implements Job{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("开始清除无效订单！");
        JobDataMap map=context.getJobDetail().getJobDataMap();
        OrderService orderService= (OrderService) map.get("os");
        orderService.clearOrder();
    }
}
