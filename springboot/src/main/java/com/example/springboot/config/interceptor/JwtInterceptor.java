package com.example.springboot.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.springboot.common.CodeMsg;
import com.example.springboot.entity.back.User;
import com.example.springboot.entity.fore.ForeUser;
import com.example.springboot.service.back.UserService;
import com.example.springboot.service.fore.ForeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class JwtInterceptor implements HandlerInterceptor {


    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
//        如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
//        执行认证
        if(StrUtil.isBlank(token)){
            throw CodeMsg.HOMEUSER_TOKEN_ERROR;
        }
//        获取 token 中的 userId
        String userId;
        try{
            userId = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException j){
            throw CodeMsg.HOMEUSER_TOKEN_FAILURE_ERROR;
        }
        User user = userService.findById(Long.valueOf(userId));
        if(user==null){
            throw CodeMsg.HOMEUSER_NO_EXIST;
        }
//        用户密码加签验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try{
            jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
            throw CodeMsg.HOMEUSER_TOKEN_FAILURE_ERROR;
        }
        return true;
    }
}
