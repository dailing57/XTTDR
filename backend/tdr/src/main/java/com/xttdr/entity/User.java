package com.xttdr.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String workId;
    private String name;
    private String schoolId;
    private String avatar;
}
