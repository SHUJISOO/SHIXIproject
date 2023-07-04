package com.shujisoo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shujisoo.entity.Carousel;
import com.shujisoo.mapper.CarouselMapper;
import com.shujisoo.service.CarouselService;
import org.springframework.stereotype.Service;

@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {
}
