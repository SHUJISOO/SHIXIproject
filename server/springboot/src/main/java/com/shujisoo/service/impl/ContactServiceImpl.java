package com.shujisoo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shujisoo.entity.Contact;
import com.shujisoo.mapper.ContactMapper;
import com.shujisoo.service.ContactService;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {
}
