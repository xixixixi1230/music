package com.example.yin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.yin.common.R;
import com.example.yin.model.domain.ListSong;
import com.example.yin.model.request.ListSongRequest;
import org.springframework.stereotype.Service;

public interface ListSongService extends IService<ListSong> {
    R allListSong();

    R songBySongListId(Integer songListId);

    R addSong(ListSong addListSong);

    R deleteSongById(Integer id);

    R deleteSongByBody(ListSongRequest listSongRequest);
}
