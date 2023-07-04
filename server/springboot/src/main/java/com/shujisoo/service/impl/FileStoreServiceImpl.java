package com.shujisoo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shujisoo.entity.FileStore;
import com.shujisoo.mapper.FileStoreMapper;
import com.shujisoo.service.FileStoreService;
import org.springframework.stereotype.Service;

@Service
public class FileStoreServiceImpl extends ServiceImpl<FileStoreMapper, FileStore> implements FileStoreService {
}
