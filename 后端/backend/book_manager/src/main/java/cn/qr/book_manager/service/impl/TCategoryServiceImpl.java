package cn.qr.book_manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.qr.book_manager.pojo.TCategory;
import cn.qr.book_manager.service.TCategoryService;
import cn.qr.book_manager.mapper.TCategoryMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ASUS
* @description 针对表【t_category】的数据库操作Service实现
* @createDate 2025-10-23 22:28:25
*/
@Service
public class TCategoryServiceImpl extends ServiceImpl<TCategoryMapper, TCategory>
    implements TCategoryService{

    @Resource
    private TCategoryMapper tCategoryMapper;

    @Override
    public List<TCategory> listAll() {
        return tCategoryMapper.selectCategoryAndBook();
    }
}




