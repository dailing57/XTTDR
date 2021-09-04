package com.xttdr.cotroller;

import com.xttdr.common.Result;
import com.xttdr.service.CourseService;
import com.xttdr.service.CourseServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    CourseServiceImpl courseService;

    @GetMapping("/{id}")
    Result<?> getCoursesByPage(@PathVariable String id,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "6") Integer pageSize){
        return courseService.getCoursesByPage(pageNum,pageSize,id);
    }
}
