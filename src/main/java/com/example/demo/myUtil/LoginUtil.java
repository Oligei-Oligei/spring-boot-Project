package com.example.demo.myUtil;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
public class LoginUtil {

    @Autowired
    private UserMapper userMapper;
    /*根据传入的cookie池以及需要查找的cookie获取到对应的用户*/
    public User getUserByCookie(Cookie[] cookies, String tag){
        if (cookies != null) {
            for (Cookie cookie : cookies){
                if (cookie.getName().equals(tag)){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    /*找到user*/
                    if (user != null){
                        return user;
                    }
                    /*没有找到user*/
                    break;
                }
            }
        }
        return null;
    }
}
