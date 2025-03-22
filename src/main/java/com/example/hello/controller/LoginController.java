package com.example.hello.controller;

import com.example.hello.common.Result;
import com.example.hello.dto.LoginDTO;
import com.example.hello.dto.LoginVO;
import com.example.hello.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = loginService.login(loginDTO);
        return Result.success(loginVO);
    }
} 