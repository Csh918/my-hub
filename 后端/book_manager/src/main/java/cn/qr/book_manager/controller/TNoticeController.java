package cn.qr.book_manager.controller;

import cn.qr.book_manager.pojo.TNotice;
import cn.qr.book_manager.service.TNoticeService;
import cn.qr.book_manager.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公告管理
 */
@RestController
@RequestMapping("/notice")
public class TNoticeController {

    @Resource
    private TNoticeService noticeService;

    /**
     * 获取最新的公告
     */
    @GetMapping("/getLatest")
    public R getLatest(){
        QueryWrapper<TNotice> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("notice_id");
        return R.success("获取最新公告成功",noticeService.list(queryWrapper).get(0));
    }
}
