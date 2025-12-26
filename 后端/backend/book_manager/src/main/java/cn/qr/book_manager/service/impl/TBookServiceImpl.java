package cn.qr.book_manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.qr.book_manager.pojo.TBook;
import cn.qr.book_manager.service.TBookService;
import cn.qr.book_manager.mapper.TBookMapper;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【t_book】的数据库操作Service实现
* @createDate 2025-10-23 22:28:25
*/
@Service
public class TBookServiceImpl extends ServiceImpl<TBookMapper, TBook>
    implements TBookService{

}




