package com.example.yin.model.daomain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@TableName(value = "song")
@Data
public class Song {
    // 主键自增
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer singerId;

    private String name;

    private String introduction;

    // 插入时自动增加创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    // 插入时自动更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private String pic;

    private String lyric;

    private String url;

    // TODO 是做什么用的
//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this);
//    }
}
