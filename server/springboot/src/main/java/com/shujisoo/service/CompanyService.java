package com.shujisoo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shujisoo.entity.Company;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService extends IService<Company> {
//    Page<Company> findCompanyPage(Page<Company> objectPage);

//    List<Company> findAllList();
//
//    List<Company> findInfoById(Integer id);

//    List<Company> findResumeById(Integer id);

    Page<Company> findResumeById(Page<Company> objectPage, Integer id);

    Page<Company> findJobList(Integer id, String jobName, Page<Company> objectPage);

    List<Company> getOneCompanyInfo(Integer id);

    Page<Company> findCompanyList(Page<Company> objectPage);

//    void setSendResume(Integer companyId, Integer applicantId);

//    List<Company> findAllJobs();
}
