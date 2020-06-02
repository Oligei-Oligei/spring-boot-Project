package com.example.demo.myUtil;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 * @author 有梦想的咸鱼
 * 一切与错误信息处理有关的公共代码
 */
@Component
public class ErrorMessage {
    /*处理publish页面的错误信息提示*/
    public Model publishErrorMessage(Model model, String tag, String message){
        return model.addAttribute(tag, message);
    }
}
