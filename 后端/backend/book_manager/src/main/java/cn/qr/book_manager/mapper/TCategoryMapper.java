package cn.qr.book_manager.mapper;

import cn.qr.book_manager.pojo.TCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author ASUS
* @description 针对表【t_category】的数据库操作Mapper
* @createDate 2025-10-23 22:28:25
* @Entity cn.qr.book_manager.pojo.TCategory
*/
public interface TCategoryMapper extends BaseMapper<TCategory> {

    /**
     * 查询所有类别和图书信息
     * @return
     */
    List<TCategory> selectCategoryAndBook();

}




