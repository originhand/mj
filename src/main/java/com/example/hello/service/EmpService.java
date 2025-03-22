package com.example.hello.service;

import com.example.hello.common.PageBean;
import com.example.hello.dto.EmpPageQueryDTO;
import com.example.hello.entity.Emp;
import java.util.List;

public interface EmpService {
    PageBean<Emp> page(EmpPageQueryDTO queryDTO);
    void delete(List<Integer> ids);
    void add(Emp emp);
    Emp getById(Integer id);
    void update(Emp emp);
    List<Emp> list();
} 