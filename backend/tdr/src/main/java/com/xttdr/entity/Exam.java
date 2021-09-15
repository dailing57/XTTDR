package com.xttdr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("exam")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exam {
    @TableId("exam_id")
    private String examId;
    private String name;
    private String courseId;
    private Date createdTime;
    private String teacherId;
    private Date begintime;
    private String lasttime;
}

