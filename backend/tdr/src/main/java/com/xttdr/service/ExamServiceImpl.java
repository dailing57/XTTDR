package com.xttdr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xttdr.common.Result;
import com.xttdr.entity.*;
import com.xttdr.mapper.DoExamMapper;
import com.xttdr.mapper.ExamMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("ExamServiceImpl")
public class ExamServiceImpl implements ExamService {

    //等待学生-课程表

    @Resource
    ExamMapper examMapper;

    @Resource
    DoExamMapper doExamMapper;

    @Resource(name = "ProblemServiceImpl")
    ProblemService problemService;

    @Resource
    CourseServiceImpl courseService;

    @Override
    public Result<?> getExamById(String examId) {
        return Result.success(examMapper.selectById(examId));
    }

    @Override
    public Result<?> getExamByUserId(Integer pageNum,Integer pageSize,String userId, String search) {
        return Result.success(examMapper.getExamByUserId(new Page<Exam>(pageNum, pageSize), userId, search));
    }

    @Override
    public Result<?> updateExam(Exam exam) {
        examMapper.updateById(exam);
        return Result.success();
    }

    @Override
    public Result<?> addExam(Exam exam) {
        examMapper.insert(exam);
        return Result.success();
    }

    @Override
    public Result<?> deleteExamById(String examId) {
        examMapper.deleteById(examId);
        QueryWrapper<DoExam> queryWrapper = new QueryWrapper<DoExam>().eq("exam_id",examId);
        doExamMapper.delete(queryWrapper);
        return Result.success();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<?> scoreExam(DoExam doExam, List<String> answer) {
        Exam exam = (Exam)getExamById(doExam.getExamId()).getData();
        List<Problem> problems = (List<Problem>)(problemService.getProblemByExamId(exam.getExamId()).getData());
        Integer score = 0;
        for(int i = 0; i < answer.size(); i++)
            if(answer.get(i).equals(problems.get(i).getAnwser()))
                score++;
        doExam.setScore(score*10);
        QueryWrapper<DoExam> queryWrapper = new QueryWrapper<DoExam>().eq("exam_id", doExam.getExamId()).eq("id",doExam.getId());
        if(doExamMapper.update(doExam, queryWrapper) > 0)
            return Result.success();
        return Result.error("-1","上传失败");
    }

    @Override
    public Result<?> getDoExamByExamId(Integer pageNum, Integer pageSize, String examId) {
        return Result.success(doExamMapper.getDoExamByExamId(new Page<DoExam>(pageNum, pageSize), examId));
    }

    @Override
    public Result<?> getDoExamByExamId(String examId) {
        QueryWrapper<DoExam> queryWrapper = new QueryWrapper<DoExam>().eq("exam_id", examId);
        return Result.success(doExamMapper.selectList(queryWrapper));
    }

    @Override
    public Result<?> getDoExamByUserId(Integer pageNum, Integer pageSize, String userId) {
        return Result.success(doExamMapper.getDoExamByExamId(new Page<DoExam>(pageNum, pageSize), userId));
    }

    @Override
    public Result<?> getDoExamByUserId(String userId) {
        QueryWrapper<DoExam> queryWrapper = new QueryWrapper<DoExam>().eq("id", userId);
        return Result.success(doExamMapper.selectList(queryWrapper));
    }

    @Override
    public Result<?> getTeacherIdByPaperId(String paperId) {
        QueryWrapper<Exam> queryWrapper = new QueryWrapper<Exam>().eq("content",paperId);
        return Result.success(examMapper.selectOne(queryWrapper).getTeacherId());
    }

    @Override
    public Result<?> handOutExam(Exam exam) {
        return handOutExam(exam.getExamId());
    }

    @Override
    public Result<?> handOutExam(String examId) {
        String courseId = ((Exam) getExamById(examId).getData()).getCourseId();
        List<DoCourse> students = (List<DoCourse>) courseService.getStudentList(courseId).getData();
        for(DoCourse student : students){
            doExamMapper.insert(new DoExam(examId, student.getStudentId(), -1));
        }
        return Result.success();
    }
}

