package com.example.demo.controller;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 有梦想的咸鱼
 */

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") int id,
                           Model model){
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        model.addAttribute("questionDTO", questionDTO);
        return "question";
    }
}
