package com.shujisoo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shujisoo.entity.Resume;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ResumeMapper extends BaseMapper<Resume> {
    @Select("select * from resume where user_id = #{userId}")
    List<Resume> getResumeByUserId(@Param("userId") Integer userId);
}
