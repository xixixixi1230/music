package com.example.yin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.yin.common.R;
import com.example.yin.model.domain.Collect;
import com.example.yin.model.request.CollectRequest;

public interface CollectService extends IService<Collect> {


    R addCollection(Integer userId, Integer songId, Integer songListId, Byte type);



    R existCollection(Integer userId, Integer songId, Integer songListId, Integer type);

    R deleteCollect(Integer userId, Integer songId, Integer songListId, Integer type);

    R collectionOfUser(Integer userId);
}
