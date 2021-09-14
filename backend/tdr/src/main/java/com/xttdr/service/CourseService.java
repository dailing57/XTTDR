package com.xttdr.service;

import com.xttdr.common.Result;
import com.xttdr.entity.Course;

import java.util.List;

public interface CourseService {
    Result<?> getCoursesByPage(Integer pageNum,Integer pageSize,String id,String search);
    Result<?> getCourseByCourseId(String id);
    Result<?> getStudentList(String courseId);
    Result<?> addCourse(Course course);
    Result<?> updateCourse(Course course);
    Result<?> deleteStudyByTeacher(String courseId,String id);
    Result<?> deleteStudy(String id);
    Result<?> deleteByCourseId(String id);
    Result<?> deleteBatch(List<String> ids);
    Result<?> addStudy(String courseId,String id);
    Integer courseCount(String id);
}
