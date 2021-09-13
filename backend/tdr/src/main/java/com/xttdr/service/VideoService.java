package com.xttdr.service;

import com.xttdr.common.Result;
import com.xttdr.entity.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {
    Result<?> getVideosByCourseId(String courseId);
    Result<?> getVideoListByPage(Integer pageNum,Integer pageSize,String courseId);
    Result<?> addVideo(String courseId, String name,Integer order, List<MultipartFile> files) throws IOException;
    Result<?> deleteVideo(String videoId);
}
