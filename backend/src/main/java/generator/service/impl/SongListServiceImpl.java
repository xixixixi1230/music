package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.SongList;
import generator.service.SongListService;
import generator.mapper.SongListMapper;
import org.springframework.stereotype.Service;

/**
* @author zhangxinyi
* @description 针对表【song_list】的数据库操作Service实现
* @createDate 2024-08-28 17:08:51
*/
@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList>
    implements SongListService{

}




