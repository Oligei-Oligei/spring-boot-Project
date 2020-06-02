package com.example.demo.myUtil;

import com.example.demo.dto.PageDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 有梦想的咸鱼
 */

@Component
public class QuestionUtil {
    @Autowired
    private UserMapper userMapper;



    /*构造pageDTO对象时重复代码*/
    public PageDTO getPageDTO(List<Question> questionList, int totalPage, int page){
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        /*获取并构造questionDTO对象保存到列表中*/
        for (Question question : questionList) {
            User user = userMapper.findByAccountID(question.getCreator());
            if (user == null){
                continue;
            }
            /*Service内置的一个对象，用于将question中属性的数据复制到questionDTO中，只复制question和questionDTO共有的属性*/
            QuestionDTO questionDTO = new QuestionDTO();
            /*spring boot 提供的方法*/
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        /*创建一个保存数据的对象*/
        PageDTO pageDTO = new PageDTO();
        pageDTO.setQuestions(questionDTOList);
        pageDTO.setParam(totalPage, page);

        return pageDTO;
    }

}
