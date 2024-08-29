package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Singer;
import generator.service.SingerService;
import generator.mapper.SingerMapper;
import org.springframework.stereotype.Service;

/**
* @author zhangxinyi
* @description 针对表【singer】的数据库操作Service实现
* @createDate 2024-08-28 16:42:45
*/
@Service
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer>
    implements SingerService{

}




