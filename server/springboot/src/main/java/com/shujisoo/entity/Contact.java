package com.shujisoo.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "contact")
public class Contact {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String content;
    private String rate;
}
