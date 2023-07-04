package com.shujisoo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shujisoo.entity.Job;
import com.shujisoo.entity.User;
import com.shujisoo.mapper.JobMapper;
import com.shujisoo.mapper.UserMapper;
import com.shujisoo.service.JobService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {
    @Resource
    private JobMapper jobMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Page<Job> findAllListPage(Page<Job> page,String jobName,String address, String requirement) {
        return jobMapper.findAllListPage(page,jobName,address,requirement);
    }

    @Override
    public List<Job> findAllList() {
        return jobMapper.findAllList();
    }

    @Override
    public List<Job> findInfoById(Integer id) {
        return jobMapper.findInfoById(id);
    }

    @Override
    public void setSendResume(Integer jobId, Integer applicantId) {
        jobMapper.setSendResume(jobId, applicantId);
    }

    @Override
    public void setCancelResume(Integer jobId, Integer applicantId) {
        jobMapper.deleteSendResume(jobId, applicantId);
    }

    @Override
    public void setAgreeOrRefuseResume(Integer jobId, Integer applicantId, Integer accept) {
        jobMapper.setAgreeOrRefuseResume(jobId, applicantId, accept);
    }

//    @Override
//    public Page<User> findResumeById(Page<User> page, Integer id) {
//        return userMapper.findResumeById(page,id);
//    }
}