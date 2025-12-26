package cn.qr.book_manager.config;

import cn.qr.book_manager.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 注入Spring管理的TokenInterceptor Bean
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 直接使用注入的拦截器，而非new TokenInterceptor()
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/api/wx/**","/api/history/**")
                .excludePathPatterns("/api/wx/login");
    }
}