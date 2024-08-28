package com.example.yin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.yin.common.R;
import com.example.yin.model.daomain.Song;
import org.springframework.stereotype.Service;

@Service
public interface SongService extends IService<Song> {
    R allSong();
}
