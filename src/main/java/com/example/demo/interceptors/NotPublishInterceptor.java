package com.example.demo.interceptors;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 有梦想的咸鱼
 */

@Service
public class NotPublishInterceptor implements HandlerInterceptor {
    /*在请求被处理前，希望在处理请求前先检查用户是否已登录*/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        request.getSession().setAttribute("page", "NOT");        return true;
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
