package com.example.yin.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.yin.model.domain.Consumer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerMapper extends BaseMapper<Consumer> {
    int verifyPassword(@Param("username") String username, @Param("password") String password);
    Consumer getByUsername(@Param("username") String username);
}
