package com.shujisoo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shujisoo.common.Result;
import com.shujisoo.entity.Resume;
import com.shujisoo.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resume")
public class ResumeController {
    @Autowired
    private ResumeService resumeService;

    // 新增和修改
    @PostMapping
    public Result save(@RequestBody Resume resume) {
        // 新增或者更新
        return Result.success(resumeService.saveOrUpdate(resume));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(resumeService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) { // [1,2,3]
        return Result.success(resumeService.removeByIds(ids));
    }

    // 查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(resumeService.list());
    }

    // 查询单条数据
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(resumeService.getById(id));
    }

    // 根据userId查询单条数据
    @GetMapping("/oneResume/{userId}")
    public Result findResume(@PathVariable Integer userId) {
        return Result.success(resumeService.getResumeByUserId(userId));
    }

    // 分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Resume> queryWrapper = new QueryWrapper<>();
        return Result.success(resumeService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}
