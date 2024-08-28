package com.example.yin.service;

import com.example.yin.model.domain.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderManager {
    void sendCode(String code,String reciveAddress);
}