package com.xttdr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xttdr.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    IPage<Course> getCourseByAccountId(Page<?> page, @Param("id") String id,@Param("search") String search);
}
