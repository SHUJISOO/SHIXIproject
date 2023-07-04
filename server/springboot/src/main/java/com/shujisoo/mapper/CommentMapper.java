package com.shujisoo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shujisoo.entity.Comment;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> allInfo();
}
