package com.xttdr.service;

import com.xttdr.common.Result;
import com.xttdr.entity.Homework;

public interface HomeworkService {
    Result<?> getHomeworksByPage(Integer pageNum,Integer pageSize,String id);
    Result<?> getHomeworkByHomeworkId(String id);
    Result<?> addHomework(Homework homework);
    Result<?> updateHomework(Homework homework);
    Result<?> deleteByHomeworkId(String id);
}
