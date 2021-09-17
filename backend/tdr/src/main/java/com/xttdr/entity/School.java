package com.xttdr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("school")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class School {
    @TableId("school_id")
    private String schoolId;
    private String name;
}
