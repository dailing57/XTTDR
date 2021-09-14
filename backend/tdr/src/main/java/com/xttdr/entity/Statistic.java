package com.xttdr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistic {
    private Double homeworkScore;
    private Double examScore;
    private Integer homeworkSubmitCount;
    private Integer discussCount;
    private Integer courseCount;
}
