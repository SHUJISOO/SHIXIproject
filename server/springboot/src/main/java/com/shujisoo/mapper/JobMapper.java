package com.shujisoo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shujisoo.entity.Company;
import com.shujisoo.entity.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JobMapper extends BaseMapper<Job> {
    Page<Job> findAllListPage(Page<Job> page,@Param("jobName") String jobName, @Param("address") String address, @Param("requirement") String requirement);
    List<Job> findAllList();

    List<Job> findInfoById(@Param("id") Integer id);

    void setSendResume(@Param("jobId")Integer jobId, @Param("applicantId")Integer applicantId);

    void deleteSendResume(@Param("jobId")Integer jobId, @Param("applicantId")Integer applicantId);

    void setAgreeOrRefuseResume(@Param("jobId")Integer jobId, @Param("applicantId")Integer applicantId, @Param("accept") Integer accept);

//    Page<User> findResumeById(Page<User> page, @Param("id") Integer id);
}
