package com.xttdr.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xttdr.common.Result;
import com.xttdr.entity.DoHomework;
import com.xttdr.entity.Homework;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface HomeworkService {
    Result<?> getHomeworksByPage(Integer pageNum,Integer pageSize,String id);
    Result<?> getHomeworkByHomeworkId(String id);
    Result<?> addHomework(Homework homework);
    Result<?> updateHomework(Homework homework);
    Result<?> deleteByHomeworkId(String id);
    Result<?> submitHomework(String homeworkId, String studentId, List<MultipartFile> files) throws IOException;
    Result<?> submittedHomework(Integer pageNum,Integer pageSize,String homeworkId);
    Result<?> deleteSubmit(String homeworkId,String path, String id);
    Result<?> updateScore(String homeworkId, String id,Double score);
}
