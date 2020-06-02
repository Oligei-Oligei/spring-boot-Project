package com.example.demo.provider;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import javax.xml.bind.util.JAXBSource;
import java.io.IOException;
import java.util.Objects;


/**
 *
 */
// 注解 Component 仅仅将被修饰的类初始化到 Spring 容器的上下文，即将被修饰类初始化为 bean类
@Component
public class GithubProvider {
    /*用于请求密钥 accessToken */
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO).getBytes(), mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = Objects.requireNonNull(response.body()).string();
            String[] tokenString = str.split("&");
            String token = tokenString[0].split("=")[1];
            return token;
        }catch (Exception ignored){
        }
        return null;
    }
    /*用于获取用户数据 */
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            return JSON.parseObject(string, GithubUser.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}











































