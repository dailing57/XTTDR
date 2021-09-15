package com.xttdr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("paper")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paper {
    String examId;
    String problemId;
}

