package com.example.yin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.yin.common.R;
import com.example.yin.model.domain.RankList;
import com.example.yin.model.request.RankListRequest;

import java.util.List;

public interface RankListService extends IService<RankList> {

    R addRank(RankListRequest rankListAddRequest);

    R modifyRank(RankListRequest rankListAddRequest);

    R rankOfSongListId(Long songListId);

    R getUserRank(Long consumerId, Long songListId);

    List<Long> getAllSongListIds();
}
