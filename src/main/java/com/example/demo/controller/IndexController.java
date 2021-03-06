package com.example.demo.controller;

import com.example.demo.dto.PageDTO;
import com.example.demo.model.User;
import com.example.demo.myUtil.LoginUtil;
import com.example.demo.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 有梦想的咸鱼、
 * index.html 对应的控制器
 * 利用model可以将控制器中的数据传递到前端页面中
 */
@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "size", defaultValue = "5") int size){
       /*设置发布按钮*/
        request.getSession().setAttribute("page", "index");
        /*获取文章列表及分页信息*/
        PageDTO pageNation = questionService.questionSelect(page, size);
        model.addAttribute("pageNation", pageNation);
        /*返回首页*/
        return "index";
    }
}
