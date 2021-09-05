package com.xttdr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        return Result.success(courseMapper.getCourseByAccountId(new Page<Course>(pageNum,pageSize),id));
    }

    @Override
    public Result<?> getCourseByCourseId(String id) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",id);
        return Result.success(courseMapper.selectOne(queryWrapper));
    }

    @Override
    public Result<?> addCourse(Course course) {
        System.out.println(course);
        return Result.success(courseMapper.insert(course));
    }

    @Override
    public Result<?> deleteByCourseId(String courseId) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        courseMapper.delete(queryWrapper);
        return Result.success();
    }

    @Override
    public Result<?> deleteBatch(List<String> ids) {
        System.out.println(ids);
        courseMapper.deleteBatchIds(ids);
        return Result.success();
    }
}
