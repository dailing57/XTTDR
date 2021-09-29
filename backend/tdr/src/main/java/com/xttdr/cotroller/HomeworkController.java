package com.xttdr.cotroller;
import com.xttdr.common.Result;
import com.xttdr.entity.DoHomework;
import com.xttdr.entity.Homework;
import com.xttdr.service.HomeworkServiceImpl;
import com.xttdr.utils.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

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
    @PostMapping("/update")
    public Result<?> updateHomework(@RequestBody Homework homework){
        return homeworkService.updateHomework(homework);
    }
    @PostMapping("/submit")
    public Result<?> submitHomework(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String homeworkId = params.getParameter("homeworkId");
        String studentId = params.getParameter("studentId");
        return homeworkService.submitHomework(homeworkId,studentId,files);
    }
    @GetMapping("/submittedHomework")
    public Result<?> submittedHomework(@RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                       @RequestParam(defaultValue = "") String homeworkId){
        System.out.println(pageNum+" "+pageSize+" "+homeworkId);
        return homeworkService.submittedHomework(pageNum,pageSize,homeworkId);
    }
    @PostMapping("/deleteSubmit")
    public Result<?> deleteSubmit(@RequestParam String homeworkId,
                                  @RequestParam String path,
                                  @RequestParam String id){
        return homeworkService.deleteSubmit(homeworkId,path,id);
    }
    @PostMapping("/submitPoints")
    public Result<?> submitPoints(@RequestParam String homeworkId,
                                  @RequestParam String id,
                                  @RequestParam double score){
        return homeworkService.updateScore(homeworkId,id,score);
    }
}
