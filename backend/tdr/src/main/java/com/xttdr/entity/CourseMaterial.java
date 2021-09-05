package com.xttdr.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@TableName("course_material")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseMaterial {
    private String materialId;
    private String courseId;
    private String materialPath;
    private String teacherId;
    private Date CreatedTime;
}
