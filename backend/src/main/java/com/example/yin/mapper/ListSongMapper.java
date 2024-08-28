package com.example.yin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.yin.model.domain.ListSong;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListSongMapper extends BaseMapper<ListSong> {

    @Select("select * from list_song")
    List<ListSong> selectAllListSongs();
}
