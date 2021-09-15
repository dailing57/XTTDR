package com.xttdr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("doexam")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoExam {
    private String examId;
    private String id;
    private Integer score;
}

