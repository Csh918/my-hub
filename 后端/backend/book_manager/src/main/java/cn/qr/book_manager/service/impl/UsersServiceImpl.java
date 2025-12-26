package cn.qr.book_manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.qr.book_manager.pojo.Users;
import cn.qr.book_manager.service.UsersService;
import cn.qr.book_manager.mapper.UsersMapper;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【users(用户表)】的数据库操作Service实现
* @createDate 2025-10-30 21:38:43
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{

}




