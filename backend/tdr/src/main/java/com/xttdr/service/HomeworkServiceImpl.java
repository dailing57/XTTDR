package com.xttdr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xttdr.common.Result;
import com.xttdr.entity.DoCourse;
import com.xttdr.entity.DoHomework;
import com.xttdr.entity.Homework;
import com.xttdr.mapper.DoCourseMapper;
import com.xttdr.mapper.DoHomeworkMapper;
import com.xttdr.mapper.HomeworkMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HomeworkServiceImpl implements HomeworkService{
    @Resource
    DoHomeworkMapper doHomeworkMapper;
    @Resource
    HomeworkMapper homeworkMapper;

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
}
