package com.example.yin.controller;

import com.example.yin.common.R;
import com.example.yin.model.domain.ListSong;
import com.example.yin.model.request.ListSongRequest;
import com.example.yin.service.ListSongService;
import com.example.yin.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    // 歌单增加歌曲
    @PostMapping("/listSong/add")
    public R addSong(@RequestBody ListSong listSong){
        return listSongService.addSong(listSong);
    }

    // 歌单删除歌曲
    @PostMapping("/listSong/delete/id")
    public R deleteSongById(@RequestParam Integer id){
        return listSongService.deleteSongById(id);
    }

    @PostMapping("/listSong/delete/body")
    public R deleteSongByBody(@RequestBody ListSongRequest listSongRequest){
        return listSongService.deleteSongByBody(listSongRequest);
    }
}
