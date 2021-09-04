package com.xttdr.service;

import com.xttdr.common.Result;

public interface CourseService {
    Result<?> getCoursesByPage(Integer pageNum,Integer pageSize,String search);

}
