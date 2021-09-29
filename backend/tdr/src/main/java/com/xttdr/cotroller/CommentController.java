package com.xttdr.cotroller;

import cn.hutool.core.util.IdUtil;
import com.xttdr.common.Result;
import com.xttdr.entity.Comment;
import com.xttdr.service.CommentServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController{
    @Resource
    CommentServiceImpl commentService;

    @GetMapping
    public Result<?> getCommentsByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                       @RequestParam(defaultValue = "") String courseId){
        return commentService.getCommentsByPage(pageNum,pageSize,courseId);
    }
    @PostMapping("/add")
    public Result<?> addComment(@RequestBody Comment comment){
        comment.setCreatedTime(new Date());
        comment.setId(getAccountId());
        comment.setCommentId(IdUtil.fastSimpleUUID());
        return commentService.addComment(comment);
    }
    @PostMapping("/update")
    public Result<?> updateComment(@RequestBody Comment comment){
        return commentService.updateComment(comment);
    }
    @PostMapping("/delete/{commentId}")
    public Result<?> deleteComment(@PathVariable String commentId){
        return commentService.deleteComment(commentId);
    }

}
