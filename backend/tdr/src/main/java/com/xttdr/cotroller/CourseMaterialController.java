package com.xttdr.cotroller;

import com.xttdr.common.Result;
import com.xttdr.entity.CourseMaterial;
import com.xttdr.service.CourseMaterialServiceImpl;
import com.xttdr.utils.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courseMaterial")
public class CourseMaterialController extends BaseController{
    @Resource
    CourseMaterialServiceImpl courseMaterialService;
    @GetMapping("/{courseId}")
    public Result<?> getCourseMaterialByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             @PathVariable String courseId){
        System.out.println(courseId);
        return courseMaterialService.getCourseMaterialByPage(pageNum,pageSize,courseId);
    }
    @GetMapping("/search/{courseMaterialId}")
    public Result<?> getCourseMaterialById(@PathVariable String courseMaterialId){
        return courseMaterialService.getCourseMaterialById(courseMaterialId);
    }
    @PostMapping("/add")
    public Result<?> addCourseMaterial(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        if(getUser().getUserType().equals("student")){
            return Result.error("-1","没有权限");
        }
        String courseId = params.getParameter("courseId");
        CourseMaterial courseMaterial = new CourseMaterial();
        FileUtils fileUtils = new FileUtils();
        for (MultipartFile file : files) {
            courseMaterial.setMaterialId(courseId+file.getOriginalFilename());
            courseMaterial.setCourseId(courseId);
            courseMaterial.setMaterialPath(fileUtils.upload(file,courseId));
            courseMaterial.setName(file.getOriginalFilename());
            courseMaterial.setCreatedTime(new Date());
            courseMaterial.setTeacherId(getUser().getId());
            courseMaterialService.addCourseMaterial(courseMaterial);
        }
        return Result.success();
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
        FileUtils fileUtils = new FileUtils();
        fileUtils.deleteFile(courseMaterialId);
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
