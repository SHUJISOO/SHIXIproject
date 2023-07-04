package com.shujisoo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shujisoo.controller.dto.UserPasswordDTO;
import com.shujisoo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    Page<User> findPage(Page<User> page, @Param("username") String username, @Param("nickname") String nickname, @Param("role") String role);

    @Update("update user set password = #{newPassword} where username = #{username} and password = #{password}")
    int updatePassword(UserPasswordDTO userPasswordDTO);

    Page<User> findResumeById(Page<User> page, @Param("id") Integer id,@Param("nickname") String nickname,@Param("jobName") String jobName);

    List<User> findDeliveryOnes(@Param("id") Integer id);
}
