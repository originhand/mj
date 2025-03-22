package com.example.hello.service.impl;

import com.example.hello.common.PageBean;
import com.example.hello.dto.EmpPageQueryDTO;
import com.example.hello.entity.Emp;
import com.example.hello.entity.EmpExpr;
import com.example.hello.mapper.EmpMapper;
import com.example.hello.mapper.EmpExprMapper;
import com.example.hello.service.EmpService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;

    @Override
    public PageBean<Emp> page(EmpPageQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());
        Page<Emp> page = (Page<Emp>) empMapper.list(queryDTO);
        return new PageBean<>(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional
    public void delete(List<Integer> ids) {
        ids.forEach(id -> empExprMapper.deleteByEmpId(id));
        empMapper.deleteByIds(ids);
    }

    @Override
    @Transactional
    public void add(Emp emp) {
        empMapper.insert(emp);
        if (emp.getExprList() != null) {
            emp.getExprList().forEach(expr -> {
                expr.setEmpId(emp.getId());
                empExprMapper.insert(expr);
            });
        }
    }

    @Override
    public Emp getById(Integer id) {
        Emp emp = empMapper.getById(id);
        if (emp != null) {
            emp.setExprList(empExprMapper.getByEmpId(id));
        }
        return emp;
    }

    @Override
    @Transactional
    public void update(Emp emp) {
        empMapper.update(emp);
        empExprMapper.deleteByEmpId(emp.getId());
        if (emp.getExprList() != null) {
            emp.getExprList().forEach(expr -> {
                expr.setEmpId(emp.getId());
                empExprMapper.insert(expr);
            });
        }
    }

    @Override
    public List<Emp> list() {
        return empMapper.listAll();
    }
} 