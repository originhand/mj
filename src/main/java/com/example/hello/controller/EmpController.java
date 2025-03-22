package com.example.hello.controller;

import com.example.hello.common.Result;
import com.example.hello.dto.EmpPageQueryDTO;
import com.example.hello.entity.Emp;
import com.example.hello.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    private EmpService empService;

    @GetMapping
    public Result<?> page(EmpPageQueryDTO queryDTO) {
        if (queryDTO.getPage() == null) queryDTO.setPage(1);
        if (queryDTO.getPageSize() == null) queryDTO.setPageSize(10);
        return Result.success(empService.page(queryDTO));
    }

    @DeleteMapping
    public Result<?> delete(String ids) {
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        empService.delete(idList);
        return Result.success();
    }

    @PostMapping
    public Result<?> add(@RequestBody Emp emp) {
        empService.add(emp);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        return Result.success(empService.getById(id));
    }

    @PutMapping
    public Result<?> update(@RequestBody Emp emp) {
        empService.update(emp);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(empService.list());
    }
} 