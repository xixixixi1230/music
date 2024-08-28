package com.example.yin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yin.common.R;
import com.example.yin.mapper.SongMapper;
import com.example.yin.model.daomain.Song;
import com.example.yin.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {
    @Autowired
    private SongMapper songMapper;

    @Override
    public R allSong() {
        List<Song> songList=songMapper.selectList(null);
        Map<String, List<Song>> result = new HashMap<>();
        result.put("result", songList);
        return R.success(null, result);
    }
}
