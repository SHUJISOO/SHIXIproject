package com.shujisoo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.dreamyoung.mprelation.AutoLazy;
import com.github.dreamyoung.mprelation.JoinColumn;
import com.github.dreamyoung.mprelation.Lazy;
import com.github.dreamyoung.mprelation.OneToOne;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@AutoLazy
@TableName(value = "user")
public class User {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String username;
    @JsonIgnore     //返回的json数据即不包含该密码
    private String password;
    private String nickname;
    private String degree;
    private String school;
    private String phone;
    private String email;
    @TableField("file_url")
    private String fileUrl;
    @TableField("avatar_url")
    private String avatarUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("create_time")
    private Date createTime;
    private String role;
    @TableField("company_id")
    private Integer companyId;

    @TableField(exist = false)
    private List<Job> jobs;
//    @TableField(exist = false)
//    private List<Accept> accepts;
//    @TableField(exist = false)
//    private List<Company> companys;
//    @TableField(exist = false)
//    private List<Accept> accepts;
//    @TableField(exist = false)
//    private String a;

//    @TableField(exist = false)
//    private List<Course> courses;
//
//    @TableField(exist = false)
//    private List<Course> stuCourses;
//    private Integer COMPANYid;

//    private Integer companyId;
//    @TableField(exist = false)
//    @OneToOne
//    @JoinColumn(name = "companyId", referencedColumnName = "id")
//    private Company companies;

}

