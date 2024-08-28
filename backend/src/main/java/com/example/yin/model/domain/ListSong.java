package com.example.yin.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "list_song")
@Data
public class ListSong {
    // 主键自增
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer songId;

    private Integer songListId;
}
