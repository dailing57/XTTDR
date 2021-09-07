package com.xttdr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xttdr.common.Result;
import com.xttdr.entity.Course;
import com.xttdr.entity.DoCourse;
import com.xttdr.mapper.CourseMapper;
import com.xttdr.mapper.DoCourseMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{
    @Resource
    CourseMapper courseMapper;

    @Resource
    DoCourseMapper doCourseMapper;

    @Override
    public Result<?> getCoursesByPage(Integer pageNum, Integer pageSize, String id,String search) {
        return Result.success(courseMapper.getCourseByAccountId(new Page<Course>(pageNum,pageSize),id,search));
    }

    @Override
    public Result<?> getCourseByCourseId(String id) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",id);
        return Result.success(courseMapper.selectOne(queryWrapper));
    }

    @Override
    public Result<?> addCourse(Course course) {
        courseMapper.insert(course);
        return Result.success();
    }

    @Override
    public Result<?> updateCourse(Course course) {
        return Result.success(courseMapper.updateById(course));
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
        courseMapper.deleteBatchIds(ids);
        return Result.success();
    }

    @Override
    public Result<?> addStudy(String courseId,String id) {
        DoCourse doCourse = new DoCourse(courseId,id);
        QueryWrapper<DoCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.eq("student_id",id);
        DoCourse res = doCourseMapper.selectOne(queryWrapper);
        if(res != null){
            return Result.error("-2","你已经加入这门课");
        }
        if(doCourseMapper.insert(doCourse)==1){
            return Result.success();
        }
        return Result.error("-1","课程库中没有该课程");
    }
}
