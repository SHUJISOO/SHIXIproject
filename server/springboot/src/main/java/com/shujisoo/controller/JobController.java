package com.shujisoo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shujisoo.common.Result;
import com.shujisoo.entity.Company;
import com.shujisoo.entity.FileStore;
import com.shujisoo.entity.Job;
import com.shujisoo.entity.User;
import com.shujisoo.mapper.FileStoreMapper;
import com.shujisoo.mapper.JobMapper;
import com.shujisoo.service.JobService;
import com.shujisoo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    @Resource
    private JobMapper jobMapper;

    // 新增和修改
    @PostMapping
    public Result save(@RequestBody Job job) {
        // 新增或者更新
        return Result.success(jobService.saveOrUpdate(job));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(jobService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) { // [1,2,3]
        QueryWrapper<Job> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<Job> jobs = jobMapper.selectList(queryWrapper);
        for (Job job : jobs) {
            job.setIsDelete(true);
            jobMapper.updateById(job);
        }
        return Result.success();

//        return Result.success(jobService.removeByIds(ids));
    }

    // 传递用户id和企业id
    @PostMapping("/send/{jobId}/{applicantId}")
    public Result sendResume(@PathVariable Integer jobId, @PathVariable Integer applicantId) {
        jobService.setSendResume(jobId,applicantId);
        return Result.success();
    }

    // 传递用户id和企业id
    @PostMapping("/cancel/{jobId}/{applicantId}")
    public Result cancelResume(@PathVariable Integer jobId, @PathVariable Integer applicantId) {
        jobService.setCancelResume(jobId,applicantId);
        return Result.success();
    }

    // 传递用户id和企业id
    @PostMapping("/agreeOrRefuse/{jobId}/{applicantId}/{accept}")
    public Result agreeResume(@PathVariable Integer jobId, @PathVariable Integer applicantId,@PathVariable Integer accept) {
        jobService.setAgreeOrRefuseResume(jobId,applicantId,accept);
        return Result.success();
    }

//    // 查询单条联合数据
//    @GetMapping("/resumeInfo/{id}")
//    public Result findResumeInfo(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @PathVariable Integer id) throws ParseException {
//        Page<User> page = userService.findResumeById(new Page<>(pageNum, pageSize),id);
//        return Result.success(page);
//    }

    // 查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(jobService.list());
    }

    // 查询所有联合数据
    @GetMapping("/jobInfo")
    public Result findAllInfo() {
        return Result.success(jobService.findAllList());
    }

    // 查询所有联合数据
    @GetMapping("/jobInfo/page")
    public Result findAllInfoPage (@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize,
                                  @RequestParam String jobName,
                                   @RequestParam String address,
                                  @RequestParam String requirement) {
        Page<Job> page = jobService.findAllListPage(new Page<>(pageNum, pageSize),jobName,address,requirement);
        return Result.success(page);
    }

    // 查询单条联合数据
    @GetMapping("/jobInfo/{id}")
    public Result findOneInfo(@PathVariable Integer id) {
        return Result.success(jobService.findInfoById(id));
    }

    // 查询单条数据
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(jobService.getById(id));
    }

    // 分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Job> queryWrapper = new QueryWrapper<>();
        return Result.success(jobService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}