package com.example.yin.controller;

import com.example.yin.common.R;
import com.example.yin.model.request.CollectAddRequest;
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
    public R addCollection(@RequestBody CollectAddRequest collectAddRequest) {
        Integer userId=collectAddRequest.getUserId();
        Integer songId=collectAddRequest.getSongId();
        System.out.println(userId+songId);
        return collectService.addCollection(userId, songId);
    }

    /**
     * 取消收藏的歌曲或者歌单
     */
    @PostMapping("/collection/delete")
    public R deleteCollection(@RequestBody CollectAddRequest collectAddRequest) {
        Integer userId=collectAddRequest.getUserId();
        Integer songId=collectAddRequest.getSongId();
        System.out.println(userId+songId);
        return collectService.deleteCollect(userId, songId);
    }

    /**
     * 判断是否收藏歌曲或歌单
     */
    @PostMapping("/collection/status")
    public R isCollection(@RequestBody CollectAddRequest collectAddRequest) {
        Integer userId=collectAddRequest.getUserId();
        Integer songId=collectAddRequest.getSongId();
        System.out.println("userId:"+userId);
        System.out.println("songId:"+songId);
        return collectService.existCollection(userId, songId);
    }

    /**
     * 返回指定用户 ID 收藏的列表
     */
    @GetMapping("/collection/detail")
    public R collectionOfUser(@RequestParam Integer userId) {
        return collectService.collectionOfUser(userId);
    }
}
