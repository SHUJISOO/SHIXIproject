package com.shujisoo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@TableName(value = "comment")
public class Comment {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String comment;
    @TableField("user_id")
    private Integer userId;
    private String username;
    @TableField("avatar_url")
    private String avatarUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("comment_time")
    private Date commentTime;
    @TableField("community_id")
    private Integer communityId;
    @TableField("comment_id")
    private Integer commentId;
    @TableField("reply_id")
    private Integer replyId;

    @TableField(exist = false)
    private List<User> users;

}
