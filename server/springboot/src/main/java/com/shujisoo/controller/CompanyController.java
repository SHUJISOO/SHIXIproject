package com.shujisoo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shujisoo.common.Result;
import com.shujisoo.entity.Carousel;
import com.shujisoo.entity.Company;
import com.shujisoo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    // 新增和修改
    @PostMapping
    public Result save(@RequestBody Company company) {
        // 新增或者更新
        return Result.success(companyService.saveOrUpdate(company));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(companyService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) { // [1,2,3]
        return Result.success(companyService.removeByIds(ids));
    }

    // 查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(companyService.list());
    }

    // 查询公司+职位数据
    @GetMapping("/jobInfo/{id}")
    public Result findJobInfo(@PathVariable Integer id,
                              @RequestParam String jobName,
                              @RequestParam Integer pageNum,
                              @RequestParam Integer pageSize) {
        Page<Company> page = companyService.findJobList(id, jobName, new Page<>(pageNum, pageSize));
        return Result.success(page);
    }

//    // 查询所有联合数据
//    @GetMapping("/jobInfo")
//    public Result findAllInfo() {
//        return Result.success(companyService.findAllList());
//    }
//
//    // 查询单条联合数据
//    @GetMapping("/jobInfo/{id}")
//    public Result findOneInfo(@PathVariable Integer id) {
//        return Result.success(companyService.findInfoById(id));
//    }

//    // 查询单条联合数据
//    @GetMapping("/resumeInfo/{id}")
//    public Result findResumeInfo(@PathVariable Integer id) {
//        return Result.success(companyService.findResumeById(id));
//    }

    // 查询单条联合数据
    @GetMapping("/resumeInfo/{id}")
    public Result findResumeInfo(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @PathVariable Integer id) throws ParseException {
        Page<Company> page = companyService.findResumeById(new Page<>(pageNum, pageSize),id);
        return Result.success(page);
    }

    // 查询单条数据
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(companyService.getById(id));
    }

    // 查询单条数据
    @GetMapping("/companyInfo/{id}")
    public Result findOneCompanyInfo(@PathVariable Integer id) {
        return Result.success(companyService.getOneCompanyInfo(id));
    }

    // 分页查询
    @GetMapping("/companyList")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Company> queryWrapper = new QueryWrapper<>();
        Page<Company> page = companyService.findCompanyList(new Page<>(pageNum, pageSize));
        return Result.success(page);
    }

    // 分页查询
    @GetMapping("/page")
    public Result findCompanyList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Company> queryWrapper = new QueryWrapper<>();
        return Result.success(companyService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


//    // 查询所有职位数据
//    @GetMapping("/jobs")
//    public Result findAllJob() {
//        return Result.success(companyService.findAllJobs());
//    }

}