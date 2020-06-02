package com.example.demo.controller;

import com.example.demo.dto.PageDTO;
import com.example.demo.model.User;
import com.example.demo.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 有梦想的咸鱼
 */
@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name="action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value = "size", defaultValue = "5") int size,
                          @RequestParam(value = "page", defaultValue = "1") int page){
        /*cookies验证登录过程，后期使用拦截器实现*/
        /*获取cookie使用request，这个步骤是获取cookie判断用户是否登录成功然后从数据库中获取用户的信息到传递到网页上*/
     /*   Cookie[] cookies = request.getCookies();
        User user = null;
        *//*防止程序因为cookies为null而报错宕机*//*
        if (cookies != null){
            user = loginUtil.getUserByCookie(cookies, "token");
            if (user != null){
                request.getSession().setAttribute("user", user);
            } else {
                return "redirect:/";
            }
        }*/
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        /*实现点击导向栏进行页面局部切换功能*/
        if ("questions".contains(action)){
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        } else if ("replies".contains(action)){
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        /*存放数据*/
//        获取指定用户的分页数据
        PageDTO pageDTO = questionService.selectUserQuestion(user.getAccountId(), page, size);
        model.addAttribute("pageDTO", pageDTO);
        return "profile";
    }
}
