package com.example.yin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.yin.common.R;
import com.example.yin.model.domain.Singer;
import org.springframework.stereotype.Service;

@Service
public interface SingerService extends IService<Singer> {
    R allSinger();

    R singerByName(String name);

    R singerBySex(Byte sex);
}
