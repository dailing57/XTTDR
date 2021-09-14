package com.xttdr.service;

import com.xttdr.common.Result;
import com.xttdr.entity.Comment;

public interface CommentService {
    Result<?> addComment(Comment comment);
    Result<?> updateComment(Comment comment);
    Result<?> deleteComment(String id);
    Result<?> getCommentsByPage(Integer pageNum,Integer pageSize, String courseId);
    Integer commentCount(String id);
}
