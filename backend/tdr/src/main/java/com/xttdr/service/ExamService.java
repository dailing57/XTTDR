package com.xttdr.service;

import com.xttdr.common.Result;
import com.xttdr.entity.Course;
import com.xttdr.entity.DoExam;
import com.xttdr.entity.Exam;

import java.util.List;

public interface ExamService {
    Result<?> getExamByUser(Integer pageNum,Integer pageSize,String id);
    Result<?> getExamById(String examId);
    Result<?> getExamByUserId(Integer pageNum,Integer pageSize,String userId, String search);
    Result<?> updateExam(Exam exam);
    Result<?> addExam(Exam exam);
    Result<?> deleteExamById(String examId);
    Result<?> scoreExam(DoExam doExam, List<String> answer);
    Result<?> getScore(String examId,String id);
    Result<?> handOutExam(Exam exam);
    Result<?> handOutExam(String examId);

    Result<?> getDoExamByUserId(String userId);
    Result<?> getDoExamByUserId(Integer pageNum, Integer pageSize, String userId);
    Result<?> getDoExamByExamId(String examId);
    Result<?> getDoExamByExamId(Integer pageNum, Integer pageSize, String examId);

    Result<?> getTeacherIdByPaperId(String paperId);

    Double getAverageScore(String id);
}

