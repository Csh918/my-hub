package cn.qr.book_manager.interceptor;

import cn.qr.book_manager.utils.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头获取Token
        String token = request.getHeader("Authorization");
        
        // 验证Token（示例逻辑）
        if (token == null || !token.startsWith("Bearer ")) {
            // Token不存在或格式错误，返回401未授权
            response.setStatus(401);
            response.getWriter().write("未登录或Token无效");
            return false;
        }
        
        // 截取真实Token（去掉"Bearer "前缀）
        String realToken = token.substring(7);
        
        // 这里可以添加Token验证逻辑（如解析JWT、查询Redis等）
        boolean isValid = validateToken(realToken);
        if (!isValid) {
            response.setStatus(401);
            response.getWriter().write("Token已过期或无效");
            return false;
        }
        
        // Token验证通过，继续执行后续控制器方法
        return true;
    }
    
    // 模拟Token验证（实际项目中替换为真实逻辑）
    private boolean validateToken(String token) {
        // 1. 若Token是JWT，解析并验证签名

        // 2. 若Token存在Redis，检查是否存在且未过期
        return jwtUtil.validateToken(token); // 假设验证通过
    }
}