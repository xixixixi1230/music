package com.example.yin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yin.common.R;
import com.example.yin.controller.MinioUploadController;
import com.example.yin.mapper.ConsumerMapper;
import com.example.yin.model.domain.Consumer;
import com.example.yin.model.domain.SongList;
import com.example.yin.model.request.ConsumerRequest;
import com.example.yin.service.ConsumerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
public class ConsumerServiceImpl extends ServiceImpl<ConsumerMapper, Consumer>
        implements ConsumerService {

    @Autowired
    private ConsumerMapper consumerMapper;
    @Autowired
    private MinioUploadController minioUploadController;

    @Override
    public boolean existUser(String username) {
        QueryWrapper<Consumer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return consumerMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 新增用户
     */
    @Override
    public R addUser(ConsumerRequest registryRequest) {
        if (this.existUser(registryRequest.getUsername())) {
            return R.warning("用户名已注册");
        }

        Consumer consumer = new Consumer();
        BeanUtils.copyProperties(registryRequest, consumer);

        // 设置注册时间为当前时间
        consumer.setCreateTime(new Date());

        // 直接使用用户输入的密码，不进行加密
        consumer.setPassword(registryRequest.getPassword());

        // 处理可选字段
        if (StringUtils.isBlank(consumer.getPhoneNum())) {
            consumer.setPhoneNum(null);
        }
        if (StringUtils.isBlank(consumer.getEmail())) {
            consumer.setEmail(null);
        }

        // 设置默认头像
        consumer.setAvator("img/avatorImages/user.jpg");

        try {
            // 检查邮箱是否重复
            if (StringUtils.isNotBlank(consumer.getEmail())) {
                QueryWrapper<Consumer> emailQuery = new QueryWrapper<>();
                emailQuery.eq("email", consumer.getEmail());
                Consumer existingConsumer = consumerMapper.selectOne(emailQuery);
                if (existingConsumer != null) {
                    return R.fatal("邮箱已被注册");
                }
            }

            // 插入新用户
            int result = consumerMapper.insert(consumer);
            if (result > 0) {
                return R.success("注册成功");
            } else {
                return R.error("注册失败");
            }
        } catch (DuplicateKeyException e) {
            return R.fatal("注册失败，数据冲突：" + e.getMessage());
        } catch (Exception e) {
            return R.fatal("注册失败，发生异常：" + e.getMessage());
        }
    }

    @Override
    public boolean verifyPassword(String username, String password) {
        // 直接进行密码比较，不加密
        QueryWrapper<Consumer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username).eq("password", password);
        return consumerMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 根据账号查询
     *
     * @param username
     */
    @Override
    public Consumer getByUsername(String username) {
        return consumerMapper.getByUsername(username);
    }

    @Override
    public Consumer findByEmail(String email) {
        QueryWrapper<Consumer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        Consumer consumer = consumerMapper.selectOne(queryWrapper);
        return consumer;
    }

    @Override
    public R updatePassword01(ConsumerRequest updatePasswordRequest) {
        Consumer consumer = new Consumer();
        consumer.setId(updatePasswordRequest.getId());
        // 直接使用用户输入的密码，不加密
        consumer.setPassword(updatePasswordRequest.getPassword());

        if (consumerMapper.updateById(consumer) > 0) {
            return R.success("密码修改成功");
        } else {
            return R.error("密码修改失败");
        }
    }
    @Override
    public R updateUserMsg(ConsumerRequest updateRequest) {
        Consumer consumer = new Consumer();
        BeanUtils.copyProperties(updateRequest, consumer);
        if (consumerMapper.updateById(consumer) > 0) {
            return R.success("修改成功");
        } else {
            return R.error("修改失败");
        }
    }
    @Override
    public R updateUserAvator(MultipartFile avatorFile, int id) {
        String fileName = avatorFile.getOriginalFilename();
        String imgPath = "/img/avatorImages/" + fileName;
        Consumer consumer = new Consumer();
        consumer.setId(id);
        consumer.setAvator(imgPath);
        String s = minioUploadController.uploadAtorImgFile(avatorFile);
        if (s.equals("File uploaded successfully!")&&consumerMapper.updateById(consumer) > 0) {
            return R.success("上传成功", imgPath);
        } else {
            return R.error("上传失败");
        }
    }

    @Override
    public R getUserById(Integer userId){
        // 构建查询条件
        QueryWrapper<Consumer> queryWrapper = new QueryWrapper<>();
        // 构建包含条件
        queryWrapper.eq("id",userId);
        return R.success(null,consumerMapper.selectList(queryWrapper));
    }
}
