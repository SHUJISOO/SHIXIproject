package com.shujisoo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.dreamyoung.mprelation.AutoLazy;
import com.github.dreamyoung.mprelation.JoinColumn;
import com.github.dreamyoung.mprelation.Lazy;
import com.github.dreamyoung.mprelation.OneToOne;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@TableName(value = "company")
public class Company {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("company_name")
    private String companyName;
    @TableField("company_address")
    private String companyAddress;
    @TableField("company_description")
    private String companyDescription;
    @TableField("company_introduction")
    private String companyIntroduction;
    @TableField("company_img")
    private String companyImg;
    private Boolean hot;
    @TableField("user_id")
    private Integer userId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("create_time")
    private Date createTime;

    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private List<User> users;
    @TableField(exist = false)
    private List<Job> jobs;
    @TableField(exist = false)
    private List<Accept> accepts;
}
