package cn.qr.book_manager.controller;

import cn.qr.book_manager.pojo.TConfig;
import cn.qr.book_manager.service.TConfigService;
import cn.qr.book_manager.utils.R;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 轮播图管理
 */
@RestController
@RequestMapping("/config")
public class TConfigController {

    @Resource
    private TConfigService tConfigService;

    /**
     * 获取所有的轮播图
     */
    @GetMapping("/getAll")
    public R getAll() {
        return R.success("获取轮播图成功",tConfigService.list());
    }
}
