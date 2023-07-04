package com.shujisoo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "carousel")
public class Carousel {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String type;
    @TableField("img_url")
    private String imgUrl;
}
