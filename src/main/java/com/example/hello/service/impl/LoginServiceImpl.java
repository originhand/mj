package com.example.hello.service.impl;

import com.example.hello.dto.LoginDTO;
import com.example.hello.dto.LoginVO;
import com.example.hello.entity.Emp;
import com.example.hello.mapper.EmpMapper;
import com.example.hello.service.LoginService;
import com.example.hello.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private EmpMapper empMapper;
    
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 1. 根据用户名查询员工
        Emp emp = empMapper.getByUsername(loginDTO.getUsername());
        
        // 2. 判断员工是否存在
        if (emp == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        // 3. 密码比对
        if (!emp.getPassword().equals(loginDTO.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        // 4. 生成JWT令牌
        String token = jwtUtils.generateToken(emp.getId(), emp.getUsername());
        
        // 5. 构建返回结果
        return LoginVO.builder()
                .id(emp.getId())
                .username(emp.getUsername())
                .name(emp.getName())
                .token(token)
                .build();
    }
} 