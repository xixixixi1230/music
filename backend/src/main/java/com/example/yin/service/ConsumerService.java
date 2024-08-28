package com.example.yin.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.yin.common.R;
import com.example.yin.model.domain.Consumer;
import com.example.yin.model.request.ConsumerRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
@Service
public interface ConsumerService extends IService<Consumer> {

    boolean existUser(String username);

    R addUser(ConsumerRequest registryRequest);

    boolean verifyPassword(String username, String password);

    Object getByUsername(String username);
    Consumer findByEmail (String email);

    R updatePassword01(ConsumerRequest updatePasswordRequest);

    R updateUserMsg(ConsumerRequest updateRequest);

    R updateUserAvator(MultipartFile avatorFile, int id);
}
