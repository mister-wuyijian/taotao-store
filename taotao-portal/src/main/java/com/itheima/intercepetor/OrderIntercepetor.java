package com.itheima.intercepetor;

import com.itheima.pojo.User;
import com.itheima.utils.CookieUtil;
import com.itheima.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 *  @项目名：  taotao-store 
 *  @包名：    com.itheima.intercepetor
 *  @文件名:   OrderIntercepetor
 *  @创建者:   Administrator
 *  @创建时间:  2018/12/11 10:47
 *  @描述：    TODO
 */
@Component
public class OrderIntercepetor implements HandlerInterceptor{

    @Autowired
    private RedisTemplate<String ,String> template;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket= CookieUtil.findTicket(request);
        if(StringUtils.isEmpty(ticket)){
            System.out.println("未登录拦截");
            return false;
        }
        User user= RedisUtil.findUserByTicket(template,ticket);
        if(user==null){
            System.out.println("未登录拦截");
            return false;
        }
       request.setAttribute("user",user);
        System.out.println("已登录放行");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
