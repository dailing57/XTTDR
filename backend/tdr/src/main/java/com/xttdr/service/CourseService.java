package com.xttdr.service;

import com.xttdr.common.Result;
import com.xttdr.entity.Course;

import java.util.List;

public interface CourseService {
    Result<?> getCoursesByPage(Integer pageNum,Integer pageSize,String search);
    Result<?> getCourseByCourseId(String id);
    Result<?> addCourse(Course course);
    Result<?> deleteByCourseId(String id);
    Result<?> deleteBatch(List<String> ids);
}
