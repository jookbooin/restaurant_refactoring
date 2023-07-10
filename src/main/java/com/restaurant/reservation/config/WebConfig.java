package com.restaurant.reservation.config;

import com.restaurant.reservation.web.Interceptor.LogInterceptor;
import com.restaurant.reservation.web.Interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**") //하위에 있는 전부 다
                .excludePathPatterns("/css/**","/img/**", "/*.ico", "/error"); // 모든건 허용하지만 여기는 호출 x
//
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/register", "/members/login", "/members/logout", "/css/**","/img/**", "/*.ico", "/error");
    }
}
