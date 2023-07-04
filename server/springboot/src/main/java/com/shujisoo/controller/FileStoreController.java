package com.shujisoo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shujisoo.common.Result;
import com.shujisoo.entity.FileStore;
import com.shujisoo.mapper.FileStoreMapper;
import com.shujisoo.service.FileStoreService;
import com.shujisoo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件相关接口
 */
@RestController
@RequestMapping("/file")
public class FileStoreController {
    @Autowired
    private FileStoreService fileStoreService;

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Value("${server.ip}")
    private String serverIp;

    @Resource
    private FileStoreMapper fileStoreMapper;

    /**
     * 文件上传接口
     * @param file 前端传递过来的文件
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        // 定义一个文件唯一的标识码
        String fileUUID = IdUtil.fastSimpleUUID() + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath + fileUUID);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }

        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        FileStore dbFiles = getFileByMd5(md5);
        if (dbFiles != null) {
            url = dbFiles.getUrl();
        } else {
            // 上传文件到磁盘
            file.transferTo(uploadFile);
            // 数据库若不存在重复文件，则不删除刚才上传的文件
            url = "http://" + serverIp + ":9090/file/" + fileUUID;
        }

        // 存储数据库
        FileStore saveFile = new FileStore();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024); // 单位 kb
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileStoreMapper.insert(saveFile);

        return url;
    }

    // 文件下载接口
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    // 通过文件的md5查询文件
    private FileStore getFileByMd5(String md5) {
        // 查询文件的md5是否存在
        QueryWrapper<FileStore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<FileStore> filesList = fileStoreMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }

    // 新增和修改
    @PostMapping
    public Result save(@RequestBody FileStore fileStore) {
        // 新增或者更新
        return Result.success(fileStoreService.saveOrUpdate(fileStore));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(fileStoreService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) { // [1,2,3]
//        return Result.success(fileStoreService.removeByIds(ids));
        QueryWrapper<FileStore> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<FileStore> files = fileStoreMapper.selectList(queryWrapper);
        for (FileStore file : files) {
            file.setIsDelete(true);
            fileStoreMapper.updateById(file);
        }
        return Result.success();
    }

//    // 查询所有数据
//    @GetMapping
//    public Result findAll() {
//        return Result.success(fileStoreService.list());
//    }
//
//    // 查询单条数据
//    @GetMapping("/{id}")
//    public Result findOne(@PathVariable Integer id) {
//        return Result.success(fileStoreService.getById(id));
//    }

    // 分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String type) {
        QueryWrapper<FileStore> queryWrapper = new QueryWrapper<>();
        // 查询未删除的记录
        queryWrapper.eq("is_delete", false);
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        if (!"".equals(type)) {
            queryWrapper.like("type", type);
        }
        return Result.success(fileStoreService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}

