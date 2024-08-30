package com.example.yin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yin.common.R;
import com.example.yin.mapper.SingerMapper;
import com.example.yin.model.domain.Singer;
import com.example.yin.model.domain.Song;
import com.example.yin.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements SingerService {

    @Autowired
    private SingerMapper singerMapper;

    @Override
    public R allSinger() {
        return R.success(null, singerMapper.selectList(null));
    }

    @Override
    public R singerByName(String name){
        // 构建查询条件
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        // 构建等值条件
        queryWrapper.eq("name",name);
        return R.success(null,singerMapper.selectList(queryWrapper));
    }

    @Override
    public R singerBySex(Byte sex){
        // 构建查询条件
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        // 构建等值条件
        queryWrapper.eq("sex",sex);
        return R.success(null,singerMapper.selectList(queryWrapper));
    }

}
