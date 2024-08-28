package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Song;
import generator.service.SongService;
import generator.mapper.SongMapper;
import org.springframework.stereotype.Service;

/**
* @author zhangxinyi
* @description 针对表【song】的数据库操作Service实现
* @createDate 2024-08-27 23:37:09
*/
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song>
    implements SongService{

}




