package com.example.demo.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 有梦想的咸鱼
 * 用于注册拦截器，有网友反映拦截器会同时拦截css和js样式请求，导致页面的css和js失效，所以在编写当前拦截器配置时可以酌情取消
 * EnableWebMvc注释，
 * */

@Configuration
/*@EnableWebMvc*/
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private PublishInterceptor publishInterceptor;
    @Autowired
    private  NotPublishInterceptor notPublishInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        /*登录拦截器*/
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
        .excludePathPatterns("/css/**","/js/**","/img/**");
        /*问题发布拦截器，在发布页面不显示发布按钮*/
        registry.addInterceptor(publishInterceptor).addPathPatterns("/publish/**")
                .excludePathPatterns("/css/**","/js/**","/img/**");
        /*问题发布拦截器，在非发布页面显示按钮*/
        registry.addInterceptor(notPublishInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/css/**","/js/**","/img/**","/publish/**");
    }
}
