package com.example.yin.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.example.yin.config.CustomDateDeserializer;
import com.example.yin.config.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

@TableName(value = "consumer")
@Data
public class Consumer {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private Byte sex;

    private String phoneNum;
    @TableField(value = "email")
    private String email;
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date birth;

    private String introduction;

    private String location;

    private String avator;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    public Consumer() {
        this.updateTime = new Date(); // 设置为当前时间
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
