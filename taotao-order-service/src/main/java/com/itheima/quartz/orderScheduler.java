package com.itheima.quartz;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.quartz
 *  @文件名:   orderScheduler
 *  @创建者:   Administrator
 *  @创建时间:  2018/12/19 22:57
 *  @描述：    TODO
 */
@Component("od")
public class orderScheduler {
    @Autowired
    private JobDetail orderJob;
    @Autowired
    private Trigger orderTrigger;
    @Autowired
    private Scheduler orderScheduler;

    public void startJob()throws  Exception{
        orderScheduler.scheduleJob(orderJob,orderTrigger);
        orderScheduler.start();
    }
    public void pauseJob()throws  Exception{
        JobKey key=new JobKey("clearOrder","order");
        orderScheduler.pauseJob(key);
    }
    public void deleteJob()throws  Exception{
        JobKey key=new JobKey("clearOrder","order");
        orderScheduler.deleteJob(key);
    }
}
