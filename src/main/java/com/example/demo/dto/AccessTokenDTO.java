package com.example.demo.dto;

import lombok.Data;

/**
 * @author 有梦想的咸鱼
 * 使用lombok的注解 @Data，被这个注解修饰的类下的属性不需要事先 get 和 set 和 toString 方法。
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
