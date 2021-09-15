package com.xttdr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xttdr.entity.Problem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProblemMapper extends BaseMapper<Problem> {
    IPage<Problem> getProblemByTeacherId(Page<?> page, @Param("id") String id);
    List<Problem> getProblemByPaperId(@Param("id") String id);
    IPage<Problem> getProblemByPaperId(Page<?> page, @Param("id") String id);
}

