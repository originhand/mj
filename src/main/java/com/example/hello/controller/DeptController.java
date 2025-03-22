package com.example.hello.controller;

import com.example.hello.common.Result;
import com.example.hello.entity.Dept;
import com.example.hello.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/depts")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping
    public Result<?> list() {
        return Result.success(deptService.list());
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        deptService.delete(id);
        return Result.success();
    }

    @PostMapping
    public Result<?> add(@RequestBody Dept dept) {
        deptService.add(dept);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        return Result.success(deptService.getById(id));
    }

    @PutMapping
    public Result<?> update(@RequestBody Dept dept) {
        deptService.update(dept);
        return Result.success();
    }
} 