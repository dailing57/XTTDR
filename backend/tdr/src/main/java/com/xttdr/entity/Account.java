package com.xttdr.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String id;
    private String pwd;
    private String userType;
    @TableField(exist = false)
    private String token;
    @TableField(exist = false)
    private User user;
}
