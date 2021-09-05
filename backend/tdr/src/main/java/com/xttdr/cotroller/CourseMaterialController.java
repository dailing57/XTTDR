package com.xttdr.cotroller;

import com.xttdr.common.Result;
import com.xttdr.entity.CourseMaterial;
import com.xttdr.service.CourseMaterialServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/courseMaterial")
public class CourseMaterialController extends BaseController{
    @Resource
    CourseMaterialServiceImpl courseMaterialService;
    @GetMapping("/{courseId}")
    public Result<?> getCourseMaterialByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "6") Integer pageSize,
                                             @PathVariable String courseId){
        return courseMaterialService.getCourseMaterialByPage(pageNum,pageSize,courseId);
    }
    @GetMapping("/search/{courseMaterialId}")
    public Result<?> getCourseMaterialById(@PathVariable String courseMaterialId){
        return courseMaterialService.getCourseMaterialById(courseMaterialId);
    }
    @PostMapping("/add")
    public Result<?> addCourseMaterial(@RequestBody CourseMaterial courseMaterial){
        if(getUser().getUserType().equals("student")){
            return Result.error("-1","没有权限");
        }
        courseMaterial.setCreatedTime(new Date());
        courseMaterial.setTeacherId(getUser().getId());
        return courseMaterialService.addCourseMaterial(courseMaterial);
    }
    @PostMapping("/update")
    public Result<?> updateCourseMaterial(@RequestBody CourseMaterial courseMaterial){
        if(getUser().getUserType().equals("student")){
            return Result.error("-1","没有权限");
        }
        return courseMaterialService.updateCourseMaterial(courseMaterial);
    }
    @PostMapping("/delete/{courseMaterialId}")
    public Result<?> deleteCourseMaterialById(@PathVariable String courseMaterialId){
        if(getUser().getUserType().equals("student")){
            return Result.error("-1","没有权限");
        }
        return courseMaterialService.deleteCourseMaterialById(courseMaterialId);
    }
    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<String> ids){
        if(getUser().getUserType().equals("student")){
            return Result.error("-1","没有权限");
        }
        return courseMaterialService.deleteBatch(ids);
    }
}
