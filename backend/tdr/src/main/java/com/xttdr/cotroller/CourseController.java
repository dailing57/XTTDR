package com.xttdr.cotroller;

import com.xttdr.common.Result;
import com.xttdr.entity.Course;
import com.xttdr.entity.DoCourse;
import com.xttdr.mapper.DoCourseMapper;
import com.xttdr.service.CourseServiceImpl;
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
    public Result<?> addStudy(@RequestParam(defaultValue = "1") String addStudyId){
        return courseService.addStudy(addStudyId, getAccountId());
    }

    @GetMapping("/search/{id}")
    public Result<?> getCourseById(@PathVariable String id){
        return courseService.getCourseByCourseId(id);
    }

    @PostMapping("/add")
    public Result<?> addCourse(@RequestBody Course course){
        if(getAccountId().equals("student")){
            return Result.error("-1","没有权限");
        }
        course.setCreatedTime(new Date());
        return courseService.addCourse(course);
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
    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<String> ids){
        if(getAccountId().equals("student")){
            return Result.error("-1","没有权限");
        }
        return courseService.deleteBatch(ids);
    }
}
