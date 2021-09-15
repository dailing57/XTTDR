package com.xttdr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xttdr.entity.DoExam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DoExamMapper extends BaseMapper<DoExam> {
    IPage<DoExam> getDoExamByExamId(Page<?> page, @Param("id") String examId);
    IPage<DoExam> getDoExamByUserId(Page<?> page, @Param("id") String examId);
}

