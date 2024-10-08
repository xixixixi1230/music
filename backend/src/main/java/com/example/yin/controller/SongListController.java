package com.example.yin.controller;

import com.example.yin.common.R;
import com.example.yin.model.domain.SongList;
import com.example.yin.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SongListController {
    @Autowired
    private SongListService songListService;

    // 返回所有歌单
    @GetMapping("/songList")
    public R allSongList() {
        return songListService.allSongList();
    }

    // 返回标题包含文字的歌单
    @GetMapping("/songList/title/detail")
    public R songListByTitle(@RequestParam String title) {
        // 模糊匹配
        return songListService.songListByTitle('%' + title + '%');
    }

    // 返回指定类型的歌单
    @GetMapping("/songList/style/detail")
    public R songListByStyle(@RequestParam String style) {
        return songListService.songListByStyle('%' + style + '%');
    }

    // 添加歌单
    @PostMapping("/songList/add")
    public R songListAdd(@RequestBody SongList songList){
        return songListService.songListAdd(songList);
    }

    // 删除歌单
    @GetMapping("/songList/delete")
    public R songListDelete(@RequestParam Integer songListId){
        return songListService.songListDelete(songListId);
    }
}
