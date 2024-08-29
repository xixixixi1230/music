package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.ListSong;
import generator.service.ListSongService;
import generator.mapper.ListSongMapper;
import org.springframework.stereotype.Service;

/**
* @author zhangxinyi
* @description 针对表【list_song】的数据库操作Service实现
* @createDate 2024-08-28 17:37:07
*/
@Service
public class ListSongServiceImpl extends ServiceImpl<ListSongMapper, ListSong>
    implements ListSongService{

}




