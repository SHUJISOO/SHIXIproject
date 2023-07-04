package com.shujisoo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shujisoo.controller.dto.UserDTO;
import com.shujisoo.controller.dto.UserPasswordDTO;
import com.shujisoo.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public interface UserService extends IService<User> {
    Page<User> findPage(Page<User> objectPage, String username, String nickname, String role);

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    void updatePassword(UserPasswordDTO userPasswordDTO);

    Page<User> findResumeById(Page<User> objectPage, Integer id,String nickname, String jobName);

    List<User> findDeliveryOnes(Integer id);

//    Object register(UserDTO userDTO);
}
