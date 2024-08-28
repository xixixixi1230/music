package com.example.yin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.yin.common.R;
import com.example.yin.model.domain.Song;
import org.springframework.stereotype.Service;

@Service
public interface SongService extends IService<Song> {
    R allSong();

    R songById(Integer id);

    R songBySingerId(Integer singerId);

    R songBySingerName(String singerName);
}
