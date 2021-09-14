package com.xttdr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xttdr.entity.DoHomework;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DoHomeworkMapper extends BaseMapper<DoHomework> {
    Double getAverageScore(@Param("id") String id);
    Integer getSubmitCount(@Param("id") String id);
    Integer getRate(@Param("id") String id,@Param("rate") Double rate);
}
