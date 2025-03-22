package com.example.hello.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmpPageQueryDTO {
    private String name;
    private Integer gender;
    private LocalDate begin;
    private LocalDate end;
    private Integer page;
    private Integer pageSize;
} 