package com.shujisoo.controller;

import com.shujisoo.common.Result;
import com.shujisoo.entity.Accept;
import com.shujisoo.service.AcceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accept")
public class AcceptController {
    @Autowired
    private AcceptService acceptService;

    // 查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(acceptService.list());
    }

    // 新增和修改
    @PostMapping
    public Result save(@RequestBody Accept accept) {
        // 新增或者更新
        return Result.success(acceptService.saveOrUpdate(accept));
    }

    // 查询单条数据
    @GetMapping("/{jobId}/{applicantId}")
    public Result findOne(@PathVariable Integer jobId, @PathVariable Integer applicantId) {
        return Result.success(acceptService.getByIds(jobId, applicantId));
    }

    @GetMapping("/{jobId}")
    public Result findOnes(@PathVariable Integer jobId) {
        return Result.success(acceptService.getOnesById(jobId));
    }
}
