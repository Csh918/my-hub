package cn.qr.book_manager.controller;

import cn.qr.book_manager.service.TCategoryService;
import cn.qr.book_manager.utils.R;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类别管理
 */
@RestController
@RequestMapping("/category")
public class TCategoryController {

    @Resource
    private TCategoryService tCategoryService;

    /**
     * 获取所有类别
     */
    @GetMapping("/getAll")
    public R getAll(){
        return R.success("获取所有类别成功",tCategoryService.list());
    }

    /**
     * 获取所有类别包含图书信息
     */
    @GetMapping("/getAllAndBook")
    public R getAllAndBook(){
        return R.success("获取所有类别和图书成功",tCategoryService.listAll());
    }

}
