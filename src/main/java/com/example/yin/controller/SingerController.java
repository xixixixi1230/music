package com.example.yin.controller;

import com.example.yin.common.R;
import com.example.yin.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SingerController {
    // 实现依赖注入
    @Autowired
    private SingerService singerService;

    // 返回所有歌手
    @GetMapping("/singer")
    public R allSinger() {
        return singerService.allSinger();
    }

    // 根据歌手名查找歌手
    @GetMapping("/singer/name/detail")
    public R singerByName(@RequestParam String name) {
        return singerService.singerByName(name);
    }

    // 根据歌手性别查找歌手
    @GetMapping("/singer/sex/detail")
    public R singerBySex(@RequestParam Byte sex){
        return singerService.singerBySex(sex);
    }
}
