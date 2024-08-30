package com.example.yin.controller;

import com.example.yin.common.R;
import com.example.yin.model.domain.SongList;
import com.example.yin.model.request.RankListRequest;
import com.example.yin.service.RankListService;
import com.example.yin.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class RankListController {

    @Autowired
    private RankListService rankListService;
    @Autowired
    private SongListService songListService;
    /**
     * 提交评分
     */
    @PostMapping("/rankList/add")
    public R addRank(@RequestBody RankListRequest rankListAddRequest) {
        return rankListService.addRank(rankListAddRequest);
    }
    /**
     * 修改评分
     */
    @PostMapping("/rankList/modify")
    public R modifyRank(@RequestBody RankListRequest rankListAddRequest) {
        return rankListService.modifyRank(rankListAddRequest);
    }
    /**
     * 获取指定歌单的评分
     */
    @GetMapping("/rankList")
    public R rankOfSongListId(@RequestParam Long songListId) {
        return rankListService.rankOfSongListId(songListId);
    }
    /**
     * 获取指定用户的歌单评分
     */
    @GetMapping("/rankList/user")
    public R getUserRank(@RequestParam(required = false) Long consumerId, @RequestParam Long songListId) {
        R userRank = rankListService.getUserRank(consumerId, songListId);
        return R.success("成功",userRank);
    }

    /**
     * 前10排序
     */
    @GetMapping("/top10")
    public List<Map<String, Object>> getTop10SongLists() {
        // 获取所有歌单的 ID
        List<Long> songListIds = rankListService.getAllSongListIds();

        // 计算每个歌单的平均分并获取其他信息
        List<Map<String, Object>> songListRanks = songListIds.stream()
                .map(songListId -> {
                    // 获取歌单的平均分
                    R rankResponse = rankListService.rankOfSongListId(songListId);
                    Double averageScore = rankResponse.getSuccess() ? ((Number) rankResponse.getData()).doubleValue() : 0.0;

                    // 获取歌单的其他信息
                    R songListResponse = songListService.getSongListById(songListId);
                    SongList songList = songListResponse.getSuccess() ? (SongList) songListResponse.getData() : null;

                    Map<String, Object> songListMap = new HashMap<>();
                    songListMap.put("songListId", songListId);
                    songListMap.put("averageScore", averageScore);

                    if (songList != null) {
                        songListMap.put("title", songList.getTitle());
                        songListMap.put("pic", songList.getPic());
                        songListMap.put("introduction", songList.getIntroduction());
                        songListMap.put("style", songList.getStyle());
                    } else {
                        songListMap.put("title", "Unknown");
                        songListMap.put("pic", "");
                        songListMap.put("introduction", "No introduction available");
                        songListMap.put("style", "Unknown");
                    }

                    return songListMap;
                })
                .sorted((a, b) -> Double.compare((Double) b.get("averageScore"), (Double) a.get("averageScore")))
                .limit(10)
                .collect(Collectors.toList());

        return songListRanks;
    }

}
