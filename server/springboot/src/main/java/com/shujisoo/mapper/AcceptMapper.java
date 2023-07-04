package com.shujisoo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shujisoo.entity.Accept;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AcceptMapper extends BaseMapper<Accept> {
    @Select("select * from job_applicant where jobId = #{jobId} and applicantId = #{applicantId}")
    List<Accept> getByIds(@Param("jobId") Integer jobId,@Param("applicantId") Integer applicantId);

    @Select("select * from job_applicant where jobId = #{jobId}")
    List<Accept> getOnesById(@Param("jobId") Integer jobId);
}
