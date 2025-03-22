package com.example.hello.mapper;

import com.example.hello.dto.EmpPageQueryDTO;
import com.example.hello.entity.Emp;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface EmpMapper {
    List<Emp> list(EmpPageQueryDTO queryDTO);
    void deleteByIds(List<Integer> ids);
    void insert(Emp emp);
    Emp getById(Integer id);
    void update(Emp emp);
    List<Emp> listAll();
    Emp getByUsername(String username);
} 