package com.example.yin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.yin.common.R;
import com.example.yin.model.domain.SongList;
import org.springframework.stereotype.Service;

@Service
public interface SongListService extends IService<SongList> {
    R allSongList();

    R songListByTitle(String title);

    R songListByStyle(String style);
    R getSongListById(Long id);
}
