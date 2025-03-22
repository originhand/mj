package com.example.hello.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginVO {
    private Integer id;
    private String username;
    private String name;
    private String token;
} 