package com.xttdr.cotroller;

import cn.hutool.core.util.IdUtil;
import com.xttdr.common.Result;
import com.xttdr.entity.Course;
import com.xttdr.entity.DoCourse;
import com.xttdr.mapper.DoCourseMapper;
import com.xttdr.service.CourseServiceImpl;
import org.apache.ibatis.annotations.Results;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController extends BaseController{
    @Resource
    CourseServiceImpl courseService;

    @GetMapping
    public Result<?> getCoursesByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "6") Integer pageSize,
                                      @RequestParam(defaultValue = "") String search){
        return courseService.getCoursesByPage(pageNum,pageSize, getAccountId(),search);
    }

    @PostMapping("/addStudy")
    public Result<?> addStudy(@RequestParam String addStudyId){
        return courseService.addStudy(addStudyId, getAccountId());
    }

    @GetMapping("/search/{id}")
    public Result<?> getCourseById(@PathVariable String id){
        return courseService.getCourseByCourseId(id);
    }

    @GetMapping("/studentList")
    public Result<?> getStudentList(@RequestParam String courseId){
        return courseService.getStudentList(courseId);
    }

    @PostMapping("/deleteStudent")
    public Result<?> deleteStudent(@RequestParam String courseId,
                                   @RequestParam String studentId){
        return courseService.deleteStudyByTeacher(courseId,studentId);
    }

    @PostMapping("/add")
    public Result<?> addCourse(@RequestParam String name){
        if(getAccountId().equals("student")){
            return Result.error("-1","没有权限");
        }
        String courseId = IdUtil.fastSimpleUUID();
        Course course = new Course(courseId,name,getAccountId(),new Date());
        courseService.addCourse(course);
        return courseService.addStudy(courseId,getAccountId());
    }
    @PostMapping("/update")
    public Result<?> updateCourse(@RequestBody Course course){
        if(getAccountId().equals("student")){
            return Result.error("-1","没有权限");
        }
        return courseService.updateCourse(course);
    }
    @PostMapping("/delete/{courseId}")
    public Result<?> deleteCourse(@PathVariable String courseId){
        if(getAccountId().equals("student")){
            return Result.error("-1","没有权限");
        }
        return courseService.deleteByCourseId(courseId);
    }
    @PostMapping("/deleteStudy/{courseId}")
    public Result<?> deleteStudy(@PathVariable String courseId){
        if(getAccountId().equals("student")){
            return Result.error("-1","没有权限");
        }
        return courseService.deleteStudy(courseId);
    }
    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<String> ids){
        if(getAccountId().equals("student")){
            return Result.error("-1","没有权限");
        }
        return courseService.deleteBatch(ids);
    }
}
