package com.xttdr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId("material_id")
    private String materialId;
    private String courseId;
    private String materialPath;
    private String teacherId;
    private String name;
    private Date CreatedTime;
}
