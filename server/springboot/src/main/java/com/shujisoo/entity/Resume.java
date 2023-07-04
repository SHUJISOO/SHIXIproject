package com.shujisoo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "resume")
public class Resume {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String education;
    private String school;
    private String experience;
    private String skill;
    private String award;
    private String other;
    @TableField("user_id")
    private Integer userId;
}
