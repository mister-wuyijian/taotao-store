package com.itheima.service;

import com.itheima.pojo.User;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service
 *  @文件名:   UserService
 *  @创建者:   Administrator
 *  @创建时间:  2018/9/11 21:23
 *  @描述：    TODO
 */
public interface UserService {
    Boolean userCheck(String param, int type);

    String selectUser(String ticket);

    int addUser(User user);

    String login(User user);
}
