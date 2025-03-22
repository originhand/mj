package com.example.hello.interceptor;

import com.example.hello.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 获取请求url
        String url = request.getRequestURI();
        // 2. 判断是否是登录请求，如果是，直接放行
        if (url.contains("/login")) {
            return true;
        }

        // 3. 获取请求头中的token
        String token = request.getHeader("token");
        
        // 4. 判断token是否存在
        if (!StringUtils.hasLength(token)) {
            response.setStatus(401);
            return false;
        }

        // 5. 解析token
        try {
            jwtUtils.parseToken(token);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }
} 