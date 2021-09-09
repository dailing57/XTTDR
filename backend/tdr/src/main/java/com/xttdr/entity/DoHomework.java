package com.xttdr.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("dohomework")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoHomework {
    private String homeworkId;
    private String id;
    private String path;
    private double score;
}
