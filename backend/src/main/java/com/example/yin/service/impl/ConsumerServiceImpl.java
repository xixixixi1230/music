package com.example.yin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yin.common.R;
import com.example.yin.mapper.ConsumerMapper;
import com.example.yin.model.domain.Consumer;
import com.example.yin.model.request.ConsumerRequest;
import com.example.yin.service.ConsumerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Date; // 确保引入了这个包

import static com.example.yin.constant.Constants.SALT;

@Service
public class ConsumerServiceImpl extends ServiceImpl<ConsumerMapper, Consumer>
        implements ConsumerService {

    @Autowired
    private ConsumerMapper consumerMapper;

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

        // MD5加密密码
        String password = DigestUtils.md5DigestAsHex((SALT + registryRequest.getPassword()).getBytes(StandardCharsets.UTF_8));
        consumer.setPassword(password);

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
        return consumerMapper.verifyPassword(username,password)>0;
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


}
