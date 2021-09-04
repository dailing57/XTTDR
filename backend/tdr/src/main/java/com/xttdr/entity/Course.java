package com.xttdr.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("course")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private String courseId;
    private String name;
    private String teacherId;
    private Date createdTime;
}
