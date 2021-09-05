package com.xttdr.cotroller;

import cn.hutool.json.JSON;
import com.xttdr.common.Result;
import com.xttdr.entity.Course;
import com.xttdr.service.CourseService;
import com.xttdr.service.CourseServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    CourseServiceImpl courseService;

    @GetMapping("/{userId}")
    Result<?> getCoursesByPage(@PathVariable("userId") String id,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "6") Integer pageSize){
        return courseService.getCoursesByPage(pageNum,pageSize,id);
    }

    @GetMapping("/search/{id}")
    Result<?> getCourseById(@PathVariable String id){
        return courseService.getCourseByCourseId(id);
    }

    @PostMapping("/add")
    Result<?> addCourse(@RequestBody Course course){
        course.setCreatedTime(new Date());
        return courseService.addCourse(course);
    }

    @PostMapping("/delete/{courseId}")
    Result<?> deleteCourse(@PathVariable String courseId){
        return courseService.deleteByCourseId(courseId);
    }

    @PostMapping("/deleteBatch")
    Result<?> deleteBatch(@RequestBody List<String> ids){
        return courseService.deleteBatch(ids);
    }
}
