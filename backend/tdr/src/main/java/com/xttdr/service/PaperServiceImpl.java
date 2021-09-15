package com.xttdr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xttdr.common.Result;
import com.xttdr.entity.Paper;
import com.xttdr.entity.Problem;
import com.xttdr.mapper.PaperMapper;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.mock.web.MockMultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service("PaperServiceImpl")
public class PaperServiceImpl implements PaperService{
    @Resource
    PaperMapper paperMapper;

    @Resource
    ProblemService problemService;

    @Override
    public Result<?> addProblemById(String examId, String problemId) {
        Paper tmp = new Paper(examId, problemId);
        if(paperMapper.insert(tmp) > 0)
            return Result.success();
        return Result.error("-1", "添加失败");
    }

    @Override
    public Result<?> addProblemById(String examId, List<String> problemId) {
        for(String str : problemId) {
            Paper tmp = new Paper(examId, str);
            paperMapper.insert(tmp);
        }
        return Result.success();
    }

    @Override
    public Result<?> deleteProblemById(String examId, String problemId) {
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exam_id", examId).eq("problem_id", problemId);
        paperMapper.delete(queryWrapper);
        return Result.success();
    }

    @Override
    public Result<?> deleteProblemById(String examId, List<String> problemId) {
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exam_id", examId).in("problem_id", problemId);
        paperMapper.delete(queryWrapper);
        return Result.success();
    }

    @Override
    public Result<?> clearPaperById(String examId) {
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exam_id", examId);
        paperMapper.delete(queryWrapper);
        return Result.success();
    }
/*
    public Result<?> copyPaperById(String examId, String newId){
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<Paper>().eq("exam_id", examId);
        List<Paper> papers = paperMapper.selectList(queryWrapper);
        for(Paper paper : papers){
            paper.setPaperId(newId);
            paperMapper.insert(paper);
        }
        return Result.success();
    }
 */

    @SuppressWarnings("unchecked")
    @Override
    public Result<?> exportPaperWithExcel(String examId) {
        HSSFWorkbook workbook = getWorkbook(examId);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try{
            workbook.write(os);
        }catch (IOException e){
            e.printStackTrace();
        }
        ByteArrayInputStream in = new ByteArrayInputStream(os.toByteArray());;
        MockMultipartFile file = null;
        try{
            file = new MockMultipartFile("files","temp.xls", "application/vnd.ms-excel", in);
        }catch (IOException e){
            e.printStackTrace();
        }
        if(file != null)
            return Result.success(file);
        return Result.error("-1","失败");
    }

    private HSSFWorkbook getWorkbook(String examId){
        List<Problem> problems = (List<Problem>) problemService.getProblemByExamId(examId).getData();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet1");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        String[] titles = {"id", "describe", "optionA", "optionB", "optionC", "optionD", "answer", "parse"};
        for(int i = 0; i < titles.length; i++){
            cell = row.createCell(i);
            cell.setCellValue(titles[i]);
        }
        for(int i = 0; i < problems.size(); i++) {
            row = sheet.createRow(i + 1);
            Problem tmp = problems.get(i);
            for(int k = 0; k < 8; k++){
                cell = row.createCell(k);
                switch(k){
                    case 0: cell.setCellValue(String.valueOf(i+1)); break;
                    case 1: cell.setCellValue(tmp.getDescribes()); break;
                    case 2: cell.setCellValue(tmp.getOptionA()); break;
                    case 3: cell.setCellValue(tmp.getOptionB()); break;
                    case 4: cell.setCellValue(tmp.getOptionC()); break;
                    case 5: cell.setCellValue(tmp.getOptionD()); break;
                    case 6: cell.setCellValue(tmp.getAnswer()); break;
                    case 7: cell.setCellValue(tmp.getParse()); break;
                }
            }
        }
        return workbook;
    }
}

