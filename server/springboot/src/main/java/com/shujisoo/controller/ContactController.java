package com.shujisoo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shujisoo.common.Result;
import com.shujisoo.entity.Contact;
import com.shujisoo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    // 新增和修改
    @PostMapping
    public Result save(@RequestBody Contact contact) {
        // 新增或者更新
        return Result.success(contactService.saveOrUpdate(contact));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(contactService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) { // [1,2,3]
        return Result.success(contactService.removeByIds(ids));
    }

    // 查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(contactService.list());
    }

    // 查询单条数据
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(contactService.getById(id));
    }

    // 分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Contact> queryWrapper = new QueryWrapper<>();
        return Result.success(contactService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}
