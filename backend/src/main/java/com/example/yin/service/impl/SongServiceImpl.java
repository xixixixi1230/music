package com.example.yin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yin.common.R;
import com.example.yin.mapper.SongMapper;
import com.example.yin.model.domain.Song;
import com.example.yin.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {
    @Autowired
    private SongMapper songMapper;

    // 返回所有歌曲
    @Override
    public R allSong() {
//        List<Song> songList=songMapper.selectList(null);
//        Map<String, List<Song>> result = new HashMap<>();
//        result.put("result", songList);
        return R.success(null, songMapper.selectList(null));
    }

    // 根据ID搜索歌曲
    @Override
    public R songById(Integer id) {
        // 构建查询条件
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        // 构建等值条件
        queryWrapper.eq("id",id);
        return R.success(null, songMapper.selectList(queryWrapper));
    }

    // 根据歌手ID搜索歌曲
    @Override
    public R songBySingerId(Integer singerId) {
        // 构建查询条件
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        // 构建等值条件
        queryWrapper.eq("singer_id",singerId);
//        List<Song> songList=songMapper.selectList(queryWrapper);
//        Map<String, List<Song>> result = new HashMap<>();
//        result.put("result", songList);
        return R.success(null, songMapper.selectList(queryWrapper));
    }

    // 根据歌手名查询歌曲
    @Override
    public R songBySingerName(String singerName){
        // 构建查询条件
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        // 构建包含条件
        queryWrapper.like("singer_name",singerName);
        return R.success(null, songMapper.selectList(queryWrapper));
    }
}
