package com.xttdr.service;

import com.xttdr.common.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PaperService {
    Result<?> addProblemById(String paperId, String problemId);
    Result<?> addProblemById(String paperId, List<String> problemId);
    Result<?> deleteProblemById(String paperId, String problemId);
    Result<?> deleteProblemById(String paperId, List<String> problemId);
    Result<?> clearPaperById(String paperId);
    Result<?> exportPaperWithExcel(String paperId);

    //Result<?> copyPaperById(String paperId, String newId); //返回新构造的试卷编号
}

