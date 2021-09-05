package com.xttdr.service;

import com.xttdr.common.Result;
import com.xttdr.entity.CourseMaterial;

import java.util.List;

public interface CourseMaterialService {
    Result<?> getCourseMaterialByPage(Integer pageNum,Integer pageSize,String search);
    Result<?> getCourseMaterialById(String courseMaterialId);
    Result<?> addCourseMaterial(CourseMaterial courseMaterial);
    Result<?> updateCourseMaterial(CourseMaterial courseMaterial);
    Result<?> deleteCourseMaterialById(String courseMaterialId);
    Result<?> deleteBatch(List<String> ids);
}
