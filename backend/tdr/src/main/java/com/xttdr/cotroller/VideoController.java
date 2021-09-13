package com.xttdr.cotroller;

import com.xttdr.common.Result;
import com.xttdr.entity.Video;
import com.xttdr.service.VideoServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController extends BaseController{
    @Resource
    VideoServiceImpl videoService;
    @GetMapping
    public Result<?> getVideosByCourseId(@RequestParam String courseId){
        return videoService.getVideosByCourseId(courseId);
    }
    @GetMapping("/list")
    public Result<?> getVideoListByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam(defaultValue = "") String courseId){
        return videoService.getVideoListByPage(pageNum,pageSize,courseId);
    }
    @PostMapping("/add")
    public Result<?> addVideo(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String name = params.getParameter(("name"));
        String courseId = params.getParameter("courseId");
        Integer order = Integer.valueOf(params.getParameter("order"));
        return videoService.addVideo(courseId,name,order,files);
    }
    @PostMapping("/delete")
    public Result<?> deleteVideo(@RequestParam String videoId){
        return videoService.deleteVideo(videoId);
    }
}
