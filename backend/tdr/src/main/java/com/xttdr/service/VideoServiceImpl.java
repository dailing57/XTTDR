package com.xttdr.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xttdr.common.Result;
import com.xttdr.entity.Video;
import com.xttdr.mapper.VideoMapper;
import com.xttdr.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService{
    @Value("${server.port}")
    private String port;

    @Value("${file.ip}")
    private String ip;
    @Resource
    VideoMapper videoMapper;

    @Override
    public Result<?> getVideosByCourseId(String courseId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId).orderByAsc("seq");
        return Result.success(videoMapper.selectList(queryWrapper));
    }

    @Override
    public Result<?> getVideoListByPage(Integer pageNum, Integer pageSize, String courseId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId).orderByAsc("seq");
        return Result.success(videoMapper.selectPage(new Page<Video>(pageNum,pageSize),queryWrapper));
    }

    @Override
    public Result<?> addVideo(String courseId, String name,Integer order, List<MultipartFile> files) throws IOException {
        FileUtils fileUtils = new FileUtils();
        MultipartFile file = files.get(0);
        String uid = IdUtil.fastSimpleUUID();
        String prefix = "video/"+ uid;
        fileUtils.upload(file,prefix);
        Video video = new Video();
        video.setName(name);
        video.setVideoId(uid+file.getOriginalFilename());
        video.setCourseId(courseId);
        video.setCreatedTime(new Date());
        video.setSeq(order);
        String path = "http://"+ip+":"+port+"/files/"+prefix+file.getOriginalFilename();
        video.setPath(path);
        System.out.println(video);
        videoMapper.insert(video);
        return Result.success();
    }

    @Override
    public Result<?> deleteVideo(String videoId) {
        FileUtils fileUtils = new FileUtils();
        fileUtils.deleteFile("video/"+videoId);
        videoMapper.deleteById(videoId);
        return Result.success();
    }
}
