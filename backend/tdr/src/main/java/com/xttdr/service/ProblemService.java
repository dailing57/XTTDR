package com.xttdr.service;

import com.xttdr.common.Result;
import com.xttdr.entity.Problem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProblemService {
    Result<?> getProblemByProblemId(String problemId);
    Result<?> getProblemByTeacherId(Integer pageNum, Integer pageSize, String teacherId);
    Result<?> getProblemByExamId(String paperId);
    Result<?> getProblemByExamId(Integer pageNum, Integer pageSize, String paperId);
    Result<?> addProblem(Problem pro);
    Result<?> deleteProblemByProblemId(String problemId);
    Result<?> deleteProblemByProblemId(List<String> problemId);
    Result<?> updateProblem(Problem pro);

    Result<?> importProblemByExcel(String teacherId, MultipartFile file);
}

