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
    public R addCollection(Integer userId, Integer songId) {
        // 根据type来判断收藏的是歌曲还是歌单
        Collect collect = new Collect();
        collect.setUserId(userId);
        collect.setCreateTime(new Date()); // 设置 createTime 为当前时间
        collect.setSongId(songId);

        if (collectMapper.insert(collect) > 0) {
            return R.success("收藏成功", true);
        } else {
            return R.error("收藏失败");
        }
    }



    @Override
    public R existCollection(Integer userId, Integer songId) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("song_id", songId);
        if (collectMapper.selectCount(queryWrapper) > 0) {
            System.out.println(true);
            return R.success("已收藏", true);
        } else {
            System.out.println(false);
            return R.success("未收藏", false);
        }
    }


    @Override
    public R deleteCollect(Integer userId, Integer songId) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("song_id", songId);
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
