package com.xttdr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("homework")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Homework {
    @TableId("homework_id")
    private String homeworkId;
    private String name;
    private String teacherId;
    private Date createdTime;
    private Date deadline;
    private String content;
    private String courseId;
}
