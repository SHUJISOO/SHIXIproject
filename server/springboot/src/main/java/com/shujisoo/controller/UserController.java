package com.shujisoo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shujisoo.common.Constants;
import com.shujisoo.common.Result;
import com.shujisoo.controller.dto.UserDTO;
import com.shujisoo.controller.dto.UserPasswordDTO;
import com.shujisoo.entity.User;
import com.shujisoo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 登录
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
//        String role     = userDTO.getRole();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }

    // 注册
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String role     = userDTO.getRole();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password) || StrUtil.isBlank(role)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(userService.register(userDTO));
    }

    // 修改密码
    @PostMapping("/password")
    public Result password(@RequestBody UserPasswordDTO userPasswordDTO) {
        userService.updatePassword(userPasswordDTO);
        return Result.success();
    }

    // 新增和修改
    @PostMapping
    public Result save(@RequestBody User user) {
        // 新增或者更新
        return Result.success(userService.saveOrUpdate(user));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(userService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) { // [1,2,3]
        return Result.success(userService.removeByIds(ids));
    }

    // 查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(userService.list());
    }

    // 查询单条数据
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    // 查询单条数据
    @GetMapping("/delivery/{id}")
    public Result deliveryOnes(@PathVariable Integer id) {
        return Result.success(userService.findDeliveryOnes(id));
    }

    // 分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String username,
                           @RequestParam(defaultValue = "") String nickname,
                           @RequestParam(defaultValue = "") String role) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!"".equals(username)) {
            queryWrapper.like("username", username);
        }
        if (!"".equals(nickname)) {
            queryWrapper.like("nickname", nickname);
        }
        if (!"".equals(role)) {
            queryWrapper.like("role", role);
        }
//        queryWrapper.orderByDesc("id");
        return Result.success(userService.findPage(new Page<>(pageNum, pageSize), username, nickname, role));
    }

    // 查询单条联合数据
    @GetMapping("/resumeInfo/{id}")
    public Result findResumeInfo(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @PathVariable Integer id,
                                 @RequestParam String nickname,
                                 @RequestParam String jobName) {
        Page<User> page = userService.findResumeById(new Page<>(pageNum, pageSize),id,nickname,jobName);
        return Result.success(page);
    }

}
