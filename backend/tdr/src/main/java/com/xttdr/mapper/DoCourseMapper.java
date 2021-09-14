package com.xttdr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xttdr.entity.DoCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DoCourseMapper extends BaseMapper<DoCourse> {
    Integer courseCount(@Param("id") String id);
}
