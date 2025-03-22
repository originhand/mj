package com.example.hello.service;

import com.example.hello.dto.LoginDTO;
import com.example.hello.dto.LoginVO;

public interface LoginService {
    LoginVO login(LoginDTO loginDTO);
} 