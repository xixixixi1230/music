package com.example.yin.controller;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.yin.common.R;
import com.example.yin.model.domain.Consumer;
import com.example.yin.model.domain.ResetPasswordRequest;
import com.example.yin.model.request.ConsumerRequest;
import com.example.yin.service.ConsumerService;
import com.example.yin.service.impl.ConsumerServiceImpl;
import com.example.yin.service.impl.SimpleOrderManager;
import com.example.yin.utils.RandomUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.example.yin.common.Consts;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;


@RestController
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    SimpleOrderManager simpleOrderManager;
    @Autowired
    private HttpSession session; // 注入 HttpSession
    /**
     * 前台页面调用 注册
     * 用户注册
     */
    @PostMapping("/user/add")
    public R addUser(@RequestBody ConsumerRequest registryRequest) {
        return consumerService.addUser(registryRequest);
    }
    /**
     * 前台页面调用 登录
     * 用户登录
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(@RequestParam("username") String username, @RequestParam("password") String password) throws JSONException {
        System.out.println(username);
        System.out.println(password);
        JSONObject jsonObject = new JSONObject();
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
            jsonObject.put(Consts.CODE,200);
            jsonObject.put(Consts.MSG,"登录成功");
            jsonObject.put("data",consumerService.getByUsername(username));
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"用户名或密码错误");
        return jsonObject;
    }


    /**
     * 重置密码
     */
    @PostMapping("/user/resetPassword")
    public R resetPassword(@RequestBody ResetPasswordRequest passwordRequest) {
        // 从Session中获取验证码及其过期时间
        String storedCode = (String) session.getAttribute("yzm");
        Long expiry = (Long) session.getAttribute("yxq");

        // 查找用户
        Consumer user = consumerService.findByEmail(passwordRequest.getEmail());

        // 验证用户和验证码
        if (user == null) {
            return R.fatal("用户不存在");
        } else if (storedCode == null || expiry == null || expiry <= System.currentTimeMillis() || !storedCode.equals(passwordRequest.getCode())) {
            return R.fatal("验证码不存在或失效");
        }

        // 更新密码
        ConsumerRequest consumerRequest = new ConsumerRequest();
        BeanUtils.copyProperties(user, consumerRequest);
        consumerRequest.setPassword(passwordRequest.getPassword());
        consumerService.updatePassword01(consumerRequest);

        // 密码更新成功后，可以选择清除Session中的验证码
        session.removeAttribute("yzm");
        session.removeAttribute("yxq");

        return R.success("密码修改成功");
    }

    /**
     * 发送验证码功能
     */
    @GetMapping("/sendVerificationCode")
    public R sendCode(@RequestParam String email, HttpSession session) {
        Consumer user = consumerService.findByEmail(email);
        if (user == null) {
            return R.fatal("用户不存在");
        }

        String code = RandomUtils.code();
        simpleOrderManager.sendCode(code, email);

        // 保存验证码到Session中
        session.setAttribute("yzm", code);
        session.setAttribute("yxq", System.currentTimeMillis() + 10 * 60 * 1000); // 5分钟有效期

        return R.success("发送成功");
    }
    /**
     * 更新用户信息
     */
    @PostMapping("/user/update")
    public R updateUserMsg(@RequestBody ConsumerRequest updateRequest) {
        return consumerService.updateUserMsg(updateRequest);
    }



    /**
     * 更新用户头像
     */
    @PostMapping("/user/avatar/update")
    public R updateUserPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id) {
        return consumerService.updateUserAvator(avatorFile, id);
    }

    @GetMapping("/user/detail")
    public R getUserById(@RequestParam("userId") Integer userId) {
        return consumerService.getUserById(userId);
    }

}
