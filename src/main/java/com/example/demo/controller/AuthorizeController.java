package com.example.demo.controller;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.provider.GithubProvider;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录功能中请求github授权登录后对返回的 code 进行解析，并根据请求结果获取access_token 获取登录用户信息
 * callback 中的 @RequestParam 用于接收 http 返回的参数 code 中的值，当前控制器可能获取的http样例如下：
 * http://127.0.0.1:8080/callback?code=28811b246ff15acb6a07&state=1。
 * 使用 okhttp工具 请求 http 数据
 */
@Controller
public class AuthorizeController {
    /*将 GithubProvider 的 bean 对象加载*/
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserService userService;

    /*对变量进行注解，和 spring 中的属性注入是一致的*/
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    /*登录功能*/
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state,
                           HttpServletResponse response){
        /*获取 github 中的 token，并获取用户对应的 github 账号信息*/
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        /*获取token的过程*/

        String token = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println(token);
        GithubUser githubUser = githubProvider.getUser(token);
        System.out.println(githubUser);
        /*设置用户的登录状态，即如果 user 不为空，则设置 session，否则不设置*/
        if (githubUser != null){
            /*创建一个 User 对象保存到数据库中*/
            User user = new User();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatar(githubUser.getAvatarUrl());
            /*将数据插入数据库*/
            userService.createOrUpdateUser(user);
            /*设置cookies*/
            response.addCookie(new Cookie("token", token));
            /*其中 redirect 用于指明使用重定向的方式跳转页面,必须使用路径的形式指出需要返回的页面，因为 index 在根路径中，所以
            * 这里使用了 "/"
            */
            /*登录成功*/
            return "redirect:/";
        } else {
            /*登录失败*/
            return "redirect:/";
        }
    }

    /*退出登录功能（后续可以考虑如何删除cookie）*/
    @GetMapping("/logout")
    public String logOut(HttpServletRequest request, HttpServletResponse response){
        /*将用户从session域中移除*/
        request.getSession().removeAttribute("user");
        /*删除cookie*/
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }
}





























