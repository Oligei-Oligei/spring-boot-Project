package com.example.demo.services;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author 有梦想的咸鱼
 */

@Component
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrUpdateUser(User user) {
        if (userMapper.findByAccountID(user.getAccountId()) == null) {
            /*设置当前用户创建时间*/
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            /*插入数据*/
            userMapper.insert(user);
        } else {
            /*设置当前用户被修改时间*/
            user.setGmtModified(System.currentTimeMillis());
            /*修改数据*/
            userMapper.alterToken(user);
        }
    }
}
