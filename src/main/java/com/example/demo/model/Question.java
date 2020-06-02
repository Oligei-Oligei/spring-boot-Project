package com.example.demo.model;

import lombok.Data;

/**
 * @author 有梦想的咸鱼
 * 使用lombok的注解 @Data，被这个注解修饰的类下的属性不需要事先 get 和 set 和 toString 方法。
 */
@Data
public class Question {
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
}
