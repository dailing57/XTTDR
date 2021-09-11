package com.xttdr.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xttdr.common.Result;
import com.xttdr.entity.Comment;
import com.xttdr.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    @Resource
    CommentMapper commentMapper;
    @Override
    public Result<?> addComment(Comment comment) {
        comment.setCreatedTime(new Date());
        commentMapper.insert(comment);
        return Result.success();
    }

    @Override
    public Result<?> updateComment(Comment comment) {
        commentMapper.updateById(comment);
        return Result.success();
    }

    @Override
    public Result<?> deleteComment(String id) {
        commentMapper.deleteById(id);
        return Result.success();
    }

    @Override
    public Result<?> getCommentsByPage(Integer pageNum, Integer pageSize, String courseId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        return Result.success(commentMapper.selectPage(new Page<>(pageNum,pageSize),queryWrapper));
    }

}
