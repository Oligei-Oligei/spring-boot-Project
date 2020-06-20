package com.example.demo.services;


import com.example.demo.dto.PageDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.myUtil.QuestionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 有梦想的咸鱼
 * service层往简单的说就是当我们一次请求数据的过程中需要关联多个表或多个动作时使用（暂时是这么理解的才对）
 * BeanUtils对象用于复制一个对象的属性的数据到另一个对象中
 */
@Component
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionUtil questionUtil;
    @Autowired
    private UserMapper userMapper;

    /*实际上这里可以使用关联查询实现的才对，不需要这么麻烦，后续酌情修改*/
    public PageDTO questionSelect(int page, int size) {
        /*获取所有数据*/
        int totalCount = questionMapper.count();
        /*计算总页数*/
        int totalPage = 0;
        if (totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = (totalCount / size) + 1;
        }
        /*防止 page 不合法*/
        if (page < 1) { page = 1; }
        if (page > totalPage) { page = totalPage; }

        /*计算分页功能中所需要的page*/
        int offset = size * (page - 1);
        /*查询分页数据*/
        List<Question> questionList = questionMapper.questionList(offset, size);
        return questionUtil.getPageDTO(questionList, totalPage, page);
    }

    public PageDTO selectUserQuestion(String userId, int page, int size) {
        /*获取用户 id 的所有问题*/
        int totalCount = questionMapper.countOfUser(userId);
        /*计算总页数*/
        int totalPage = 0;
        if (totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = (totalCount / size) + 1;
        }
        /*防止 page 不合法*/
        if (page < 1) { page = 1; }
        if (page > totalPage) { page = totalPage; }
        /*计算分页功能中所需要的page*/
        int offset = size * (page - 1);
        /*查询分页数据*/
        List<Question> questionList = questionMapper.questionUserList(userId, offset, size);
        return questionUtil.getPageDTO(questionList, totalPage, page);
    }

    public QuestionDTO getQuestionById(int id) {
        /*根据id获取对应的问题信息*/
        Question question = questionMapper.getById(id);
        /*获取问题对应的用户信息*/
        User user = userMapper.findByAccountID(question.getCreator());
        /*构建问题详情页面的所有与问题有关的信息列表*/
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setUser(user);
        BeanUtils.copyProperties(question, questionDTO);
        return questionDTO;
    }

    public void CreateOrUpdate(Question question) {
        if (question.getId() == 0) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        } else {
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }
    }
}
