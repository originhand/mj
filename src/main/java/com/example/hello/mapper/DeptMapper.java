package com.example.hello.mapper;

import com.example.hello.entity.Dept;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface DeptMapper {
    List<Dept> list();
    void delete(Integer id);
    void insert(Dept dept);
    Dept getById(Integer id);
    void update(Dept dept);
} 