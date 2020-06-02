package com.example.demo.controller;

import com.example.demo.mapper.QuestionMapper;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.myUtil.ErrorMessage;
import com.example.demo.myUtil.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用于控制publish.html 页面中的请求
 * model的作用在于设置在model中的变量可以直接在htm页面中使用，如下面的title变量可以使用 th:text="${title}"访问
 */
@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private ErrorMessage errorMessage;

    /*获取get方式的请求*/
    @GetMapping("/publish")
    public String publish(HttpServletRequest request, HttpSession session){
        User user = loginUtil.getUserByCookie(request.getCookies(), "token");
        if (user != null){
            request.getSession().setAttribute("user", user);
        }
//        i
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request, Model model){
        /*确保页面提交后出现错误时内容不会消失*/
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未注册或登录");
            return "publish";
        }

        /*判断传递的内容是否为空*/
        if (title == null || title == ""){
            model = errorMessage.publishErrorMessage(model, "error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == ""){
            model = errorMessage.publishErrorMessage(model, "error", "内容不能为空");
            return "publish";
        }
        if (tag == null || tag == ""){
            model = errorMessage.publishErrorMessage(model, "error", "标签不能为空");
            return "publish";
        }
        /*构建question对象*/
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getAccountId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        request.getSession().setAttribute("publish", "1");
        /*写入到数据库中*/
        questionMapper.create(question);
        return "redirect:/";
    }
}
