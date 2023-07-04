package com.shujisoo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shujisoo.entity.Community;
import com.shujisoo.mapper.CommunityMapper;
import com.shujisoo.service.CommunityService;
import org.springframework.stereotype.Service;

@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements CommunityService {
}
