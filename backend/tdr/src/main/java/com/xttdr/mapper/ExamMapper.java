package com.xttdr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xttdr.entity.DoExam;
import com.xttdr.entity.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExamMapper extends BaseMapper<Exam> {
    IPage<Exam> getExamByUserId(Page<?> page, @Param("id") String userId, @Param("search") String search);
}

