package com.example.yin.controller;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.yin.common.R;
import com.example.yin.model.domain.Consumer;
import com.example.yin.model.request.ConsumerRequest;
import com.example.yin.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import com.example.yin.common.Consts;
import javax.servlet.http.HttpServletRequest;


@RestController
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    /**
     * 前台页面调用 注册
     * 用户注册
     */
    @PostMapping("/user/add")
    public R addUser(@RequestBody ConsumerRequest registryRequest) {
        return consumerService.addUser(registryRequest);
    }

    /**
     * 前端用户登录
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(HttpServletRequest request) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String username = request.getParameter("username").trim();     //账号
        String password = request.getParameter("password").trim();     //密码
        if(username==null||username.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"用户名不能为空");
            return jsonObject;
        }
        if(password==null||password.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"密码不能为空");
            return jsonObject;
        }

        //保存到前端用户的对象中
        Consumer consumer = new Consumer();
        consumer.setUsername(username);
        consumer.setPassword(password);
        boolean flag = consumerService.verifyPassword(username,password);
        if(flag){   //验证成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"登录成功");
            jsonObject.put("userMsg",consumerService.getByUsername(username));
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"用户名或密码错误");
        return jsonObject;
    }


}
