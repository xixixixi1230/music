package com.example.yin.model.request;

import lombok.Data;

import java.util.Date;

@Data
public class CollectAddRequest {
    private Integer userId;
    private Integer songId;
//    private Date createTime;
}
