package com.example.yin.model.request;

import lombok.Data;

@Data
public class CommentLikeRequest {
    private Integer commentId;
    private Integer up;
}
