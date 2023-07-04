package com.shujisoo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shujisoo.common.Constants;
import com.shujisoo.controller.dto.UserDTO;
import com.shujisoo.controller.dto.UserPasswordDTO;
import com.shujisoo.entity.User;
import com.shujisoo.exception.ServiceException;
import com.shujisoo.mapper.UserMapper;
import com.shujisoo.service.UserService;
import com.shujisoo.utils.RandomName;
import com.shujisoo.utils.TokenUtils;
import org.apache.xmlbeans.impl.xb.ltgfmt.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RandomName randomName;

    // 登录
    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = loginUserInfo(userDTO);
        if (one != null) {
            BeanUtil.copyProperties(one, userDTO, true);
            // 设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);

//            String role = one.getRole(); // ROLE_ADMIN
//            // 设置用户的菜单列表
//            List<Menu> roleMenus = getRoleMenus(role);
//            userDTO.setMenus(roleMenus);

            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    private User loginUserInfo(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper); // 从数据库查询用户信息
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }

    private User registerUserInfo(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        queryWrapper.eq("nickname", userDTO.getNickname());
        queryWrapper.eq("role", userDTO.getRole());
        User one;
        try {
            one = getOne(queryWrapper); // 从数据库查询用户信息
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }

    // 注册
    @Override
    public User register(UserDTO userDTO) {
        User one = registerUserInfo(userDTO);
        if (one == null) {
            one = new User();
            BeanUtil.copyProperties(userDTO, one, true);
            // 默认一个普通用户的角色
//            one.setRole(RoleEnum.ROLE_USER.toString());
//            System.out.println(userDTO.getNickname());
            if(userDTO.getNickname() == null){
                one.setNickname(randomName.getRandomJianHan(5));
            }else {
                one.setNickname(userDTO.getNickname());
            }
            one.setRole(userDTO.getRole());
            save(one);  // 把 copy完之后的用户对象存储到数据库
        } else {
            throw new ServiceException(Constants.CODE_600, "该用户已存在！");
        }
        return one;
    }

    // 更新密码
    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
        int update = userMapper.updatePassword(userPasswordDTO);
        if (update < 1) {
            throw new ServiceException(Constants.CODE_600, "密码错误");
        }
    }

    @Override
    public Page<User> findResumeById(Page<User> page, Integer id, String nickname, String jobName) {
        return userMapper.findResumeById(page,id,nickname,jobName);
    }

    @Override
    public List<User> findDeliveryOnes(Integer id) {
        return userMapper.findDeliveryOnes(id);
    }

    @Override
    public Page<User> findPage(Page<User> page, String username, String nickname, String role) {
        return userMapper.findPage(page, username, nickname, role);
    }
}
