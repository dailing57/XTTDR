package com.xttdr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xttdr.common.Result;
import com.xttdr.entity.Course;
import com.xttdr.mapper.CourseMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{
    @Resource
    CourseMapper courseMapper;
    @Override
    public Result<?> getCoursesByPage(Integer pageNum, Integer pageSize, String id) {
        return Result.success(courseMapper.getCourseById(new Page<Course>(pageNum,pageSize),id));
    }
}
