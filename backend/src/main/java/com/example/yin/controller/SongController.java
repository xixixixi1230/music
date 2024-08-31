package com.example.yin.controller;

import com.example.yin.common.R;
import com.example.yin.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 这个类就被视为一个控制器，并且它的所有方法都会默认地将返回值作为 HTTP 响应体返回给客户端
@RestController
public class SongController {
    // 实现依赖注入
    @Autowired
    private SongService songService;

    // 返回所有歌曲
    @GetMapping("/song")
    public R allSong() {
        return songService.allSong();
    }

    // 返回指定ID的歌曲
    @GetMapping("/song/detail")
    public R songById(@RequestParam int id) {
        return songService.songById(id);
    }

    // 根据歌手ID搜索歌曲
    @GetMapping("/song/singerId/detail")
    public R songBySingerId(@RequestParam int singerId) {
        return songService.songBySingerId(singerId);
    }

    // 根据歌手姓名搜索歌曲
    @GetMapping("/song/singerName/detail")
    public R songBySingerName(@RequestParam String singerName) {
        return songService.songBySingerName(singerName);
    }

    @GetMapping("/song/name/detail")
    public R songByName(@RequestParam String name) {
        return songService.songByName(name);
    }
}
