package com.xttdr.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xttdr.common.Result;
import com.xttdr.entity.Problem;
import com.xttdr.mapper.ProblemMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service("ProblemServiceImpl")
public class ProblemServiceImpl implements ProblemService{
    @Resource
    private ProblemMapper problemMapper;

    @Override
    public Result<?> getProblemByProblemId(String problemId) {
        return Result.success(problemMapper.selectById(problemId));
    }

    @Override
    public Result<?> getProblemByExamId(String examId) {
        List<Problem> problems = problemMapper.getProblemByPaperId(examId);
        for (Problem problem : problems) {
            problem.setAnswer("");
        }
        return Result.success(problems);
    }

    @Override
    public Result<?> getProblemByExamId(Integer pageNum, Integer pageSize, String examId) {
        return Result.success(problemMapper.getProblemByPaperId(new Page<Problem>(pageNum,pageSize), examId));
    }

    @Override
    public Result<?> getProblemByTeacherId(Integer pageNum, Integer pageSize, String teacherId) {
        return Result.success(problemMapper.getProblemByTeacherId(new Page<Problem>(pageNum,pageSize), teacherId));
    }

    @Override
    public Result<?> addProblem(Problem pro) {
        problemMapper.insert(pro);
        return Result.success();
    }

    @Override
    public Result<?> deleteProblemByProblemId(String problemId) {
        if(problemMapper.deleteById(problemId) > 0)
            return Result.success();
        return Result.error("-1","无该元组");
    }

    @Override
    public Result<?> deleteProblemByProblemId(List<String> problemIds) {
        problemMapper.deleteBatchIds(problemIds);
        return Result.success();
    }

    @Override
    public Result<?> updateProblem(Problem pro) {
        if(problemMapper.updateById(pro)>0)
            return Result.success();
        return Result.error("-1","更新失败");
    }

    @Override
    public Result<?> importProblemByExcel(String teacherId, MultipartFile file) {
        Workbook workbook;
        Problem problem = new Problem();
        problem.setTeacherId(teacherId);
        problem.setCreatedDate(new Date());
        try{
            InputStream inputStream = file.getInputStream();
            if(file.getOriginalFilename().matches(".*(.xls|.XLS)$"))
                workbook = new HSSFWorkbook(inputStream);
            else if(file.getOriginalFilename().matches(".*(.xlsx|.XLSX)$"))
                workbook = new XSSFWorkbook(inputStream);
            else{
                return Result.error("-1", "上传文件类型有误");
            }
            Sheet sheet = workbook.getSheetAt(0);
            for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i++){
                Row row = sheet.getRow(i);
                problem.setProblemId(IdUtil.fastSimpleUUID());
                problem.setDescribes(row.getCell(1).getStringCellValue());
                problem.setOptionA(row.getCell(2).getStringCellValue());
                problem.setOptionB(row.getCell(3).getStringCellValue());
                problem.setOptionC(row.getCell(4).getStringCellValue());
                problem.setOptionD(row.getCell(5).getStringCellValue());
                problem.setAnswer(row.getCell(6).getStringCellValue());
                problem.setParse(row.getCell(7).getStringCellValue());
                addProblem(problem);
            }
        }catch (IOException e){
            e.printStackTrace();
            return Result.error("-1", "上传失败");
        }catch (NullPointerException e){
            e.printStackTrace();
            return Result.error("-1", "文件名不能为空");
        }
        return Result.success();
    }

}
