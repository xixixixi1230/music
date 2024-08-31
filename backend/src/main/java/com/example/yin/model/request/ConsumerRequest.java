package com.example.yin.model.request;

import com.example.yin.config.CustomDateDeserializer;
import com.example.yin.config.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

@Data
public class ConsumerRequest {
    private Integer id;

    private String username;

    private String oldPassword; //因为会用到用户旧密码 这无所谓的对应即可

    private String password;

    private Byte sex;

    private String phoneNum;

    private String email;
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date birth;

    private String introduction;

    private String location;

    private String avator;

    private Date createTime;
}
