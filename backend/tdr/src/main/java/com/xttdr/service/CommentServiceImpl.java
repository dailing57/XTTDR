package com.xttdr.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xttdr.common.Result;
import com.xttdr.entity.Comment;
import com.xttdr.entity.User;
import com.xttdr.mapper.CommentMapper;
import com.xttdr.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    @Resource
    CommentMapper commentMapper;
    @Resource
    UserMapper userMapper;
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
        queryWrapper.eq("course_id",courseId).orderByDesc("created_time");
        IPage<Comment> commentIPage = commentMapper.selectPage(new Page<>(pageNum,pageSize),queryWrapper);
        for (Comment record : commentIPage.getRecords()) {
            String userAvatar = userMapper.selectById(record.getId()).getAvatar();
            if(StringUtils.isNotBlank(userAvatar)){
                record.setAvatar(userAvatar);
            }else{
                record.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
            }
            String parentId = record.getParentId();
            record.setParentComment(commentMapper.selectById(parentId));
        }
        return Result.success(commentIPage);
    }

}
