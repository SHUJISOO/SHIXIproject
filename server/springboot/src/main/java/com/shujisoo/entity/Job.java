package com.shujisoo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "job")
public class Job {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("job_name")
    private String jobName;
    private String salary;
    @TableField("job_description")
    private String jobDescription;
    @TableField("job_introduction")
    private String jobIntroduction;
    private String requirement;
    @TableField("is_selected")
    private Boolean isSelected;
    @TableField("hot_job")
    private Boolean hotJob;
    @TableField("company_id")
    private String companyId;
    @TableField("is_delete")
    private Boolean isDelete;

//    private String agree;
//    private String refuse;

    @TableField(exist = false)
    private List<User> users;
    @TableField(exist = false)
    private List<Company> companys;
//    @TableField(exist = false)
//    private List<Accept> accepts;
    @TableField(exist = false)
    private Integer accepts;
    @TableField(exist = false)
    private String COMPANYNAME;
    @TableField(exist = false)
    private String COMPANYIMG;
    @TableField(exist = false)
    private String COMPANYADDRESS;
//    @TableField(exist = false)
//    private Integer accepts;
}
