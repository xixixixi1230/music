package com.example.yin.controller;

import com.example.yin.common.R;
import com.example.yin.model.request.CommentRequest;
import com.example.yin.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 提交评论
     */

    @PostMapping("/comment/add")
    public R addComment(@RequestBody CommentRequest addCommentRequest) {
        return commentService.addComment(addCommentRequest);
    }
    /**
     * 删除评论
     */

    @GetMapping("/comment/delete")
    public R deleteComment(@RequestParam Integer id) {
        return commentService.deleteComment(id);
    }
    /**
     * 获得指定歌曲 ID 的评论列表
     */

    @GetMapping("/comment/song/detail")
    public R commentOfSongId(@RequestParam Integer songId) {
        return commentService.commentOfSongId(songId);
    }
    /**
     * 获得指定歌单 ID 的评论列表
     */

    @GetMapping("/comment/songList/detail")
    public R commentOfSongListId(@RequestParam Integer songListId) {
        return commentService.commentOfSongListId(songListId);
    }
    /**
     * 点赞
     */

    @PostMapping("/comment/like/{commentId}")
    public R commentOfLike(@PathVariable Integer commentId) {
        // 调用服务层方法进行点赞操作
        return commentService.likeComment(commentId);
    }


}
