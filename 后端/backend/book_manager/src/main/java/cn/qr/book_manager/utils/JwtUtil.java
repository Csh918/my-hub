package cn.qr.book_manager.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;  // 密钥

    @Value("${jwt.expire}")
    private long expire;  // 有效期（毫秒）

    // 生成Token（基于openid）
    public String generateToken(String openid) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expire);
        return Jwts.builder()
                .setSubject(openid)  // 存储openid
                .setIssuedAt(now)   // 签发时间
                .setExpiration(expiration)  // 过期时间
                .signWith(SignatureAlgorithm.HS256, secret)  // 签名算法
                .compact();
    }

    // 解析Token获取openid
    public String getOpenidFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(cleanToken(token.substring(7)))
                .getBody();
        return claims.getSubject();
    }

    private String cleanToken(String token) {
        if (token == null) {
            return null;
        }
        // 1. 过滤所有 ASCII 控制字符（0-31 和 127）
        String cleaned = token.replaceAll("[\\x00-\\x1F\\x7F]", "");
        // 2. 过滤 Token 中可能的空白字符（前后空格、换行等）
        cleaned = cleaned.trim();
        // 3. 验证 JWT 格式（必须包含两个点）
        if (cleaned.split("\\.").length != 3) {
            throw new RuntimeException("清理后 Token 格式错误：" + cleaned);
        }
        System.out.println("cleaned:" + cleaned);
        return cleaned;
    }



    // 验证Token是否有效
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;  // 无效Token
        }
    }
}