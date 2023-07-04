package com.shujisoo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shujisoo.entity.Resume;
import com.shujisoo.mapper.ResumeMapper;
import com.shujisoo.service.ResumeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume> implements ResumeService {
    @Resource
    private ResumeMapper resumeMapper;

    @Override
    public List<Resume> getResumeByUserId(Integer userId) {
        return resumeMapper.getResumeByUserId(userId);
    }

}
