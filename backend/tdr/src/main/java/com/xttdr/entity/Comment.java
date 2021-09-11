package com.xttdr.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@TableName("comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @TableId("comment_id")
    private String commentId;
    private String content;
    private String id;
    private Date createdTime;
    private String courseId;
    private String parentId;
    @TableField(exist = false)
    private String parentComment;
    @TableField(exist = false)
    private String avatar;
}
