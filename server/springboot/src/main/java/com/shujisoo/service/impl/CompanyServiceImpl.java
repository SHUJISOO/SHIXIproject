package com.shujisoo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shujisoo.entity.Company;
import com.shujisoo.mapper.CompanyMapper;
import com.shujisoo.service.CompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {
    @Resource
    private CompanyMapper companyMapper;
//    @Override
//    public Page<Company> findCompanyPage(Page<Company> page) {
//        return companyMapper.findCompanyPage(page);
//    }

//    @Override
//    public Page<Company> findCompanyPage(Page<Company> objectPage) {
//        return null;
//    }

    @Override
    public Page<Company> findResumeById(Page<Company> page, Integer id) {
        return companyMapper.findResumeById(page,id);
    }

    @Override
    public Page<Company> findJobList(Integer id, String jobName, Page<Company> page) {
        return companyMapper.findJobList(id, jobName, page);
    }

    @Override
    public List<Company> getOneCompanyInfo(Integer id) {
        return companyMapper.getOneCompanyInfo(id);
    }

    @Override
    public Page<Company> findCompanyList(Page<Company> page) {
        return companyMapper.findCompanyList(page);
    }

//    @Override
//    public List<Company> findAllList() {
//        return companyMapper.findAllList();
//    }
//
//    @Override
//    public List<Company> findInfoById(Integer id) {
//        return companyMapper.findInfoById(id);
//    }

//    @Override
//    public List<Company> findResumeById(Integer id) {
//        return companyMapper.findResumeById(id);
//    }

//    @Override
//    public void setSendResume(Integer companyId, Integer applicantId) {
////        companyMapper.deleteSendResume(courseId, studentId);
//        companyMapper.setSendResume(companyId, applicantId);
//    }
}