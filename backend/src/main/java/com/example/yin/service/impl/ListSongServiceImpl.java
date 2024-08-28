package com.example.yin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yin.common.R;
import com.example.yin.mapper.ListSongMapper;
import com.example.yin.model.domain.ListSong;
import com.example.yin.service.ListSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListSongServiceImpl extends ServiceImpl<ListSongMapper, ListSong> implements ListSongService {
    @Autowired
    private ListSongMapper listSongMapper;

    @Override
    public R allListSong(){
        return R.success(null,listSongMapper.selectAllListSongs());
    }

    @Override
    public R songBySongListId(Integer songListId){
        QueryWrapper<ListSong> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("song_list_id",songListId);
        List<ListSong> songlist=listSongMapper.selectList(queryWrapper);
        System.out.println(songlist);
        return R.success(null,listSongMapper.selectList(queryWrapper));
    }
}
