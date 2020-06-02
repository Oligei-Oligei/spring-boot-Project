package com.example.demo.model;

import lombok.Data;

/**
 * 当前模型记录用户信息,对应 H2 数据库中的 User表
 * 使用lombok的注解 @Data，被这个注解修饰的类下的属性不需要事先 get 和 set 和 toString 方法。
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatar;
}
