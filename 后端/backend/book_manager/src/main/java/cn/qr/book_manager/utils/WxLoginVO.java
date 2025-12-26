package cn.qr.book_manager.utils;

import cn.qr.book_manager.pojo.Users;
import lombok.Data;

@Data
public class WxLoginVO {
    private String token;    // JWT令牌
    private Users users;       // 用户信息（简化版）
}