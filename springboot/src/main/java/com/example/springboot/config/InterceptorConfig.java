package com.example.springboot.config;

import com.example.springboot.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .excludePathPatterns("/fore/user/login","/system/login","/alipay/**","/fore/complaint/**"
                        ,"/fore/expressMailOrder/**","/error")
                .addPathPatterns("/**")//拦截所有请求，通过判断token是否合法来决定是否需要登录
               ;
    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}
