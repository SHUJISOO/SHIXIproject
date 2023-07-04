package com.shujisoo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shujisoo.entity.Company;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyMapper extends BaseMapper<Company> {
    Page<Company> findCompanyPage(Page<Company> page);

//    List<Company> findAllList();
//
//    List<Company> findInfoById(@Param("id") Integer id);

//    List<Company> findResumeById(@Param("id") Integer id);

    Page<Company> findResumeById(Page<Company> page, @Param("id") Integer id);

    Page<Company> findJobList(@Param("id") Integer id, @Param("jobName") String jobName,Page<Company> page);

    List<Company> getOneCompanyInfo(@Param("id") Integer id);

    Page<Company> findCompanyList(Page<Company> page);

//    void setSendResume(@Param("companyId")Integer companyId, @Param("applicantId") Integer applicantId);

//    List<Company> findAllJobs();
}
