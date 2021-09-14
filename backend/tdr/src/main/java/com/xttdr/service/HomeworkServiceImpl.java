package com.xttdr.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xttdr.common.Result;
import com.xttdr.entity.DoCourse;
import com.xttdr.entity.DoHomework;
import com.xttdr.entity.Homework;
import com.xttdr.mapper.DoCourseMapper;
import com.xttdr.mapper.DoHomeworkMapper;
import com.xttdr.mapper.HomeworkMapper;
import com.xttdr.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class HomeworkServiceImpl implements HomeworkService{
    @Resource
    DoHomeworkMapper doHomeworkMapper;
    @Resource
    HomeworkMapper homeworkMapper;

    @Override
    public Result<?> submittedHomework(Integer pageNum, Integer pageSize, String homeworkId) {
        QueryWrapper<DoHomework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id",homeworkId);
        return Result.success(doHomeworkMapper.selectPage(new Page<DoHomework>(pageNum,pageSize),queryWrapper));
    }

    @Override
    public Result<?> submitHomework(String homeworkId, String studentId, List<MultipartFile> files) throws IOException {
        Homework homework = homeworkMapper.selectById(homeworkId);
        if(homework.getDeadline().before(new Date())){
            return Result.error("-1","作业已截止");
        }
        QueryWrapper<DoHomework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id",homeworkId).eq("id",studentId);
        if(doHomeworkMapper.selectOne(queryWrapper) != null){
            return Result.error("-1","你已提交本次作业");
        }
        DoHomework doHomework = new DoHomework();
        doHomework.setHomeworkId(homeworkId);
        doHomework.setId(studentId);
        String prefix = IdUtil.fastSimpleUUID();
        FileUtils fileUtils = new FileUtils();
        String path = "";
        for (MultipartFile file : files) {
            path = file.getOriginalFilename();
            fileUtils.upload(file,prefix);
        }
        doHomework.setPath(prefix+path);
        doHomeworkMapper.insert(doHomework);
        return Result.success();
    }

    @Override
    public Result<?> getHomeworksByPage(Integer pageNum, Integer pageSize, String courseId) {
        return Result.success(homeworkMapper.getHomeworkByPage(new Page<Homework>(pageNum,pageSize),courseId));
    }

    @Override
    public Result<?> getHomeworkByHomeworkId(String id) {
        return Result.success(homeworkMapper.selectById(id));
    }

    @Override
    public Result<?> addHomework(Homework homework) {
        homework.setHomeworkId(IdUtil.fastSimpleUUID());
        homeworkMapper.insert(homework);
        return Result.success();
    }
    @Override
    public Result<?> updateHomework(Homework homework) {
        homeworkMapper.updateById(homework);
        return Result.success();
    }
    @Override
    public Result<?> deleteByHomeworkId(String id) {
        homeworkMapper.deleteById(id);
        return null;
    }

    @Override
    public Result<?> deleteSubmit(String homeworkId, String path, String id) {
        QueryWrapper<DoHomework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id",homeworkId);
        queryWrapper.eq("id",id);
        FileUtils fileUtils = new FileUtils();
        fileUtils.deleteFile(path);
        return Result.success(doHomeworkMapper.delete(queryWrapper));
    }

    @Override
    public Result<?> updateScore(String homeworkId, String id, Double score) {
        UpdateWrapper<DoHomework> doHomeworkUpdateWrapper = new UpdateWrapper<>();
        doHomeworkUpdateWrapper.eq("homework_id",homeworkId).eq("id",id).set("score",score);
        doHomeworkMapper.update(null,doHomeworkUpdateWrapper);
        return Result.success();
    }

    @Override
    public Double getAverageScore(String id) {
        return doHomeworkMapper.getAverageScore(id);
    }

    @Override
    public Integer getSubmitCount(String id) {
        return doHomeworkMapper.getSubmitCount(id);
    }

    @Override
    public Double getRate(String id, Double score) {
        return Double.valueOf(doHomeworkMapper.getRate(id, score));
    }
}
