package com.xttdr.cotroller;
import com.xttdr.common.Result;
import com.xttdr.entity.Homework;
import com.xttdr.service.HomeworkServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@RestController
@RequestMapping("/homework")
public class HomeworkController extends BaseController{
    @Resource
    HomeworkServiceImpl homeworkService;

    @GetMapping
    public Result<?> getHomeworksByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(defaultValue = "") String courseId){
        return homeworkService.getHomeworksByPage(pageNum,pageSize,courseId);
    }
    @PostMapping("/delete/{homeworkId}")
    public  Result<?> deleteByHomeworkId(@PathVariable String homeworkId){
        homeworkService.deleteByHomeworkId(homeworkId);
        return Result.success();
    }
    @PostMapping("/add")
    public Result<?> addHomework(@RequestBody Homework homework){
        return homeworkService.addHomework(homework);
    }
}
