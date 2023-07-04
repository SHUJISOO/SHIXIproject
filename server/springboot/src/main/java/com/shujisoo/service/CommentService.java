package com.shujisoo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shujisoo.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService extends IService<Comment> {
    List<Comment> allInfo();
}