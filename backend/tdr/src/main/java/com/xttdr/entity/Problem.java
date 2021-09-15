package com.xttdr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("problem")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Problem {
    @TableId("problem_id")
    private String problemId;
    private String describes;
    private String teacherId;
    private Date createdDate;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private String parse;
}

