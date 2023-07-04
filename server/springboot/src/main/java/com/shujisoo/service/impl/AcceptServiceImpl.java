package com.shujisoo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shujisoo.entity.Accept;
import com.shujisoo.mapper.AcceptMapper;
import com.shujisoo.service.AcceptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AcceptServiceImpl extends ServiceImpl<AcceptMapper, Accept> implements AcceptService {
    @Resource
    private AcceptMapper acceptMapper;
    @Override
    public List<Accept> getByIds(Integer jobId, Integer applicantId) {
        return acceptMapper.getByIds(jobId,applicantId);
    }

    @Override
    public List<Accept> getOnesById(Integer jobId) {
        return acceptMapper.getOnesById(jobId);
    }
}
