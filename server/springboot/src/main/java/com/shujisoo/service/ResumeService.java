package com.shujisoo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shujisoo.entity.Resume;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResumeService extends IService<Resume> {
    List<Resume> getResumeByUserId(Integer userId);
}
