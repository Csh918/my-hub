package cn.qr.book_manager.service;

import cn.qr.book_manager.pojo.TCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author ASUS
* @description 针对表【t_category】的数据库操作Service
* @createDate 2025-10-23 22:28:25
*/
public interface TCategoryService extends IService<TCategory> {

    /**
     * 查询所有类别和图书信息
     * @return
     */
    List<TCategory> listAll();
}
