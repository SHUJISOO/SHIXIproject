package com.shujisoo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shujisoo.entity.Accept;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AcceptService extends IService<Accept> {
    List<Accept> getByIds(Integer jobId, Integer applicantId);

    List<Accept> getOnesById(Integer jobId);
}
