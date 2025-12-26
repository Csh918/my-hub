package cn.qr.book_manager.controller;

import cn.qr.book_manager.pojo.TBook;
import cn.qr.book_manager.service.TBookService;
import cn.qr.book_manager.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

/**
 * 图书管理
 */
@RestController
@RequestMapping("/book")
public class TBookController {

    @Resource
    private TBookService tBookService;

    /**
     * 获取最新的图书
     */
    @GetMapping("/getLatest/{currentPage}/{pageSize}")
    public R getLatest(@PathVariable Integer currentPage,@PathVariable Integer pageSize){
        QueryWrapper<TBook> wrapper = new QueryWrapper<>();
        Page<TBook> page = new Page<>(currentPage,pageSize);
        wrapper.orderByDesc("book_date");
        return R.success("获取最新的图书成功",tBookService.list(page,wrapper));
    }

    /**
     * 根据类别ID获取图书
     */
    @GetMapping("/getByCid/{cid}")
    public R getByCid(@PathVariable Integer cid){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_cid",cid);
        return R.success("根据类别ID获取图书成功",tBookService.list(queryWrapper));
    }

    /**
     * 根据ID获取图书信息
     */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Integer id){
        return R.success("根据ID获取图书信息成功",tBookService.getById(id));
    }

}
