package com.example.demo.interceptors;

import com.example.demo.model.User;
import com.example.demo.myUtil.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 有梦想的咸鱼
 * 与登录有关的拦截器
 * 需要写上 Service注释或者Controller，否则自动注入的 bean（loginUtil）会报空指针异常错误
 * */
@Service
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginUtil loginUtil;

    /*在请求被处理前，希望在处理请求前先检查用户是否已登录*/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        /*获取cookie使用request，这个步骤是获取cookie判断用户是否登录成功然后从数据库中获取用户的信息到传递到网页上*/
        Cookie[] cookies = request.getCookies();
        /*防止程序因为cookies为null而报错宕机*/

        if (cookies != null){
            User user = loginUtil.getUserByCookie(cookies, "token");
            if (user != null){
                request.getSession().setAttribute("user", user);
            }
        }
        return true;
    }

    /*在请求被处理后*/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /*在请求被完成后*/
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
