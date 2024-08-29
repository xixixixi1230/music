package com.example.yin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yin.common.R;
import com.example.yin.mapper.CollectMapper;
import com.example.yin.model.domain.Collect;
import com.example.yin.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {
    @Autowired
    private CollectMapper collectMapper;

    @Override
    public R addCollection(Integer userId, Integer songId, Integer songListId, Byte type) {
        // 根据type来判断收藏的是歌曲还是歌单
        Collect collect = new Collect();
        collect.setUserId(userId);
        collect.setCreateTime(new Date()); // 设置 createTime 为当前时间
        collect.setType(type); // 设置收藏类型

        if (type == 0 && songId != null) {
            collect.setSongId(songId);
        } else if (type == 1 && songListId != null) {
            collect.setSongListId(songListId);
        } else {
            return R.error("无效的收藏类型或参数缺失");
        }

        if (collectMapper.insert(collect) > 0) {
            return R.success("收藏成功", true);
        } else {
            return R.error("收藏失败");
        }
    }



    @Override
    public R existCollection(Integer userId, Integer songId, Integer songListId, Integer type) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);

        if (type == 0 && songId != null) {
            queryWrapper.eq("song_id", songId);
        } else if (type == 1 && songListId != null) {
            queryWrapper.eq("song_list_id", songListId);
        } else {
            return R.error("无效的收藏类型或参数缺失");
        }

        if (collectMapper.selectCount(queryWrapper) > 0) {
            return R.success("已收藏", true);
        } else {
            return R.success("未收藏", false);
        }
    }


    @Override
    public R deleteCollect(Integer userId, Integer songId, Integer songListId, Integer type) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);

        // 根据type判断取消收藏的是歌曲还是歌单
        if (type == 0 && songId != null) {
            // 取消收藏歌曲
            queryWrapper.eq("song_id", songId);
        } else if (type == 1 && songListId != null) {
            // 取消收藏歌单
            queryWrapper.eq("song_list_id", songListId);
        } else {
            return R.error("无效的取消收藏请求");
        }

        if (collectMapper.delete(queryWrapper) > 0) {
            return R.success("取消收藏成功", false);
        } else {
            return R.error("取消收藏失败");
        }
    }

    @Override
    public R collectionOfUser(Integer userId) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Collect> collections = collectMapper.selectList(queryWrapper);
        return R.success("用户收藏", collections);
    }
}
