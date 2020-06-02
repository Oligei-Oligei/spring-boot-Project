package com.example.demo.dto;

import com.example.demo.model.User;
import lombok.Data;

/**
 * @author
 * 使用 lombok插件
 */
@Data
public class QuestionDTO {
    private int id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private String creator;
    private int viewCount;
    private int commentCount;
    private int likeCount;
    private User user;
}
