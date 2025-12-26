package cn.qr.book_manager.controller;

import cn.qr.book_manager.pojo.Users;
import cn.qr.book_manager.service.UsersService;
import cn.qr.book_manager.utils.HttpUtil;
import cn.qr.book_manager.utils.JwtUtil;
import cn.qr.book_manager.utils.R;
import cn.qr.book_manager.utils.WxLoginVO;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/wx")
public class WxLoginController {

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.appsecret}")
    private String appsecret;

    @Resource
    private HttpUtil httpUtil;

    @Resource
    private UsersService userService;

    @Resource
    private JwtUtil jwtUtil;

    /**
     * 微信小程序登录接口
     */
    @PostMapping("/login")
    public R wxLogin(@RequestBody Map<String, String> param) {
        String code = param.get("code");
        String nickname = (String) param.get("nickname");
        String avatar = (String) param.get("avatar");
        if (code == null || code.isEmpty()) {
            return R.fail("缺少code参数");
        }
        try {
            String url = String.format(
                "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                appid, appsecret, code
            );
            String response = httpUtil.doGet(url);
            JSONObject json = JSONObject.parseObject(response);
            if (json.containsKey("errcode") && json.getInteger("errcode") != 0) {
                return R.fail("微信登录失败：" + json.getString("errmsg"));
            }
            String openid = json.getString("openid");
            if (openid == null || openid.isEmpty()) {
                return R.fail("获取openid失败");
            }
            QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("openid",openid);
            Users user = userService.getOne(queryWrapper);
            if (user == null) {
                Users u = new Users();
                u.setOpenid(openid);
                u.setCreatedAt(new Date());
                u.setNickname(nickname);
                u.setAvatar(avatar);
                userService.save(u);  // 创建新用户
                user = u;
            }
            String token = jwtUtil.generateToken(openid);
            WxLoginVO loginVO = new WxLoginVO();
            loginVO.setToken(token);
            loginVO.setUsers(user);
            return R.success("登录成功", loginVO);
        } catch (IOException e) {
            return R.fail("网络错误：" + e.getMessage());
        } catch (Exception e) {
            return R.fail("登录失败：" + e.getMessage());
        }
    }

    @PostMapping("/upload")
    public R upload(@RequestBody Users user,@RequestHeader("Authorization") String token){
        String openId = jwtUtil.getOpenidFromToken(token);
        Users users = userService.getOne(new QueryWrapper<Users>().eq("openid", openId));
        if(user.getAvatar() != null && user.getAvatar().length() > 0){
            users.setAvatar(user.getAvatar());
        }
        if(user.getNickname() != null && user.getNickname().length() > 0){
            users.setNickname(user.getNickname());
        }
        userService.updateById(users);
        return R.success(users);
    }

}