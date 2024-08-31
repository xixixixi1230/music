package com.example.yin.controller;

import com.example.yin.common.R;
import com.example.yin.model.request.CollectRequest;
import com.example.yin.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CollectController {

    @Autowired
    private CollectService collectService;

    /**
     * 添加收藏的歌曲或者歌单
     */
    @PostMapping("/collection/add")
    public R addCollection(@RequestParam Integer userId,
                           @RequestParam(required = false) Integer songId,
                           @RequestParam(required = false) Integer songListId,
                           @RequestParam Byte type) {
        return collectService.addCollection(userId, songId, songListId, type);
    }

    /**
     * 取消收藏的歌曲或者歌单
     */
    @DeleteMapping("/collection/delete")
    public R deleteCollection(@RequestParam Integer userId,
                              @RequestParam(required = false) Integer songId,
                              @RequestParam(required = false) Integer songListId,
                              @RequestParam Integer type) {
        return collectService.deleteCollect(userId, songId, songListId, type);
    }

    /**
     * 判断是否收藏歌曲或歌单
     */
    @PostMapping("/collection/status")
    public R isCollection(@RequestParam Integer userId,
                          @RequestParam(required = false) Integer songId,
                          @RequestParam(required = false) Integer songListId,
                          @RequestParam Integer type) {
        return collectService.existCollection(userId, songId, songListId, type);
    }

    /**
     * 返回指定用户 ID 收藏的列表
     */
    @GetMapping("/collection/detail")
    public R collectionOfUser(@RequestParam Integer userId) {
        return collectService.collectionOfUser(userId);
    }
}
