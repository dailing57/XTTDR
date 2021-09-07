package com.xttdr.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("docourse")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoCourse {
    private String courseId;
    private String studentId;
}
