package com.example.yin.controller;

import com.example.yin.common.R;
import com.example.yin.service.ListSongService;
import com.example.yin.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListSongController {
    @Autowired
    private ListSongService listSongService;

    @Autowired
    private SongListService songListservice;

    @GetMapping("/listSong")
    public R allListSong(){
        return listSongService.allListSong();
    }

    // 返回指定歌单 ID 的歌曲
    @GetMapping("/listSong/detail")
    public R songBySongListId(@RequestParam int songListId) {
        return listSongService.songBySongListId(songListId);
    }
}
