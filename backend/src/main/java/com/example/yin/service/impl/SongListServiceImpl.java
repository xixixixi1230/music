package com.example.yin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yin.common.R;
import com.example.yin.mapper.ListSongMapper;
import com.example.yin.mapper.SongListMapper;
import com.example.yin.model.domain.ListSong;
import com.example.yin.model.domain.Song;
import com.example.yin.model.domain.SongList;
import com.example.yin.service.SongListService;
import com.google.j2objc.annotations.ObjectiveCName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements SongListService {
    @Autowired
    private SongListMapper songListMapper;

    @Override
    public R allSongList(){
        return R.success(null,songListMapper.selectList(null));
    }

    @Override
    public R songListByTitle(String title){
        // 构建查询条件
        QueryWrapper<SongList> queryWrapper = new QueryWrapper<>();
        // 构建包含条件
        queryWrapper.like("title",title);
        return R.success(null,songListMapper.selectList(queryWrapper));
    }

    @Override
    public R songListByStyle(String style){
        // 构建查询条件
        QueryWrapper<SongList> queryWrapper = new QueryWrapper<>();
        // 构建包含条件
        queryWrapper.like("style",style);
        return R.success(null,songListMapper.selectList(queryWrapper));
    }
    @Override
    public R getSongListById(Long id) {
        // 构建查询条件
        QueryWrapper<SongList> queryWrapper = new QueryWrapper<>();
        // 根据 ID 查询
        queryWrapper.eq("id", id);
        // 执行查询并返回结果
        SongList songList = songListMapper.selectOne(queryWrapper);
        return R.success(null, songList);
    }

    @Override
    public R songListAdd(SongList songList){
        if(songListMapper.insert(songList)>0){
            return R.success("添加歌单成功");
        }
        else{
            return R.success("添加歌单失败");
        }
    }

    @Override
    public R songListDelete(Integer songListId){
        if(songListMapper.delete(songListId)>0){
            return R.success("删除歌单成功");
        }
        else{
            return R.success("删除歌单失败");
        }
    }
}
