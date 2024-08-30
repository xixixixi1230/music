package com.example.yin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yin.common.R;
import com.example.yin.mapper.ListSongMapper;
import com.example.yin.model.domain.ListSong;
import com.example.yin.model.request.ListSongRequest;
import com.example.yin.service.ListSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListSongServiceImpl extends ServiceImpl<ListSongMapper, ListSong> implements ListSongService {
    @Autowired
    private ListSongMapper listSongMapper;

    // 返回所有歌单对应信息
    @Override
    public R allListSong(){
        return R.success(null,listSongMapper.selectAllListSongs());
    }

    // 根据歌单id寻找歌曲id
    @Override
    public R songBySongListId(Integer songListId){
        QueryWrapper<ListSong> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("song_list_id",songListId);
        List<ListSong> songlist=listSongMapper.selectList(queryWrapper);
        System.out.println(songlist);
        return R.success(null,listSongMapper.selectList(queryWrapper));
    }

    // 给歌单添加歌曲
    @Override
    public R addSong(ListSong addListSong){
        if (listSongMapper.insert(addListSong) > 0) {
            return R.success("添加成功");
        } else {
            return R.error("添加失败");
        }
    }

    @Override
    public R deleteSongById(Integer id){
        if(listSongMapper.deleteById(id)>0){
            return R.success("删除成功");
        }
        else{
            return R.success("删除失败");
        }
    }

    @Override
    public R deleteSongByBody(ListSongRequest listSongRequest){
        if(listSongMapper.deleteByBody(listSongRequest)>0){
            return R.success("删除成功");
        }
        else{
            return R.success("删除失败");
        }
    }
}
