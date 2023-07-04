package com.shujisoo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shujisoo.entity.Job;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public interface JobService extends IService<Job> {
    Page<Job> findAllListPage(Page<Job> objectPage,String jobName,String address, String requirement);

    List<Job> findAllList();

    List<Job> findInfoById(Integer id);

    void setSendResume(Integer jobId, Integer applicantId);

    void setCancelResume(Integer jobId, Integer applicantId);

    void setAgreeOrRefuseResume(Integer jobId, Integer applicantId, Integer accept);

//    Page<User> findResumeById(Page<User> objectPage, Integer id);

}