package com.xttdr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xttdr.common.Result;
import com.xttdr.entity.CourseMaterial;
import com.xttdr.mapper.CourseMaterialMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class CourseMaterialServiceImpl implements CourseMaterialService{
    @Resource
    CourseMaterialMapper courseMaterialMapper;

    @Override
    public Result<?> getCourseMaterialByPage(Integer pageNum, Integer pageSize, String search) {
        QueryWrapper<CourseMaterial> queryWrapper = new  QueryWrapper<>();
        queryWrapper.eq("course_id",search);
        return Result.success(courseMaterialMapper.selectPage(new Page<CourseMaterial>(pageNum,pageSize),queryWrapper));
    }

    @Override
    public Result<?> getCourseMaterialById(String courseMaterialId) {
        return Result.success(courseMaterialMapper.selectById(courseMaterialId));
    }

    @Override
    public Result<?> addCourseMaterial(CourseMaterial courseMaterial) {
        courseMaterialMapper.insert(courseMaterial);
        return Result.success();
    }

    @Override
    public Result<?> updateCourseMaterial(CourseMaterial courseMaterial) {
        courseMaterialMapper.updateById(courseMaterial);
        return Result.success();
    }

    @Override
    public Result<?> deleteCourseMaterialById(String courseMaterialId) {
        courseMaterialMapper.deleteById(courseMaterialId);
        return Result.success();
    }

    @Override
    public Result<?> deleteBatch(List<String> ids) {
        courseMaterialMapper.deleteBatchIds(ids);
        return Result.success();
    }
}
