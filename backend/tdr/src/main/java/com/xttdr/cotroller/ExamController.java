package com.xttdr.cotroller;

import cn.hutool.core.util.IdUtil;
import com.xttdr.common.Result;
import com.xttdr.entity.DoExam;
import com.xttdr.entity.Exam;
import com.xttdr.entity.Paper;
import com.xttdr.entity.Problem;
import com.xttdr.service.ExamService;
import com.xttdr.service.PaperService;
import com.xttdr.service.ProblemService;
import com.xttdr.utils.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExamController extends BaseController{

    @Resource(name = "PaperServiceImpl")
    private PaperService paperService;

    @Resource(name = "ExamServiceImpl")
    private ExamService examService;

    @Resource(name = "ProblemServiceImpl")
    private ProblemService problemService;

    //题库相关内容

    @GetMapping("/problems/exam")
    public Result<?> getProblemByPaperId(@RequestParam(defaultValue = "1") Integer pageNum,
                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                         @RequestParam String examId){
        return problemService.getProblemByExamId(pageNum, pageSize, examId);
    }

    @GetMapping("/problems/doexam")
    public Result<?> getProblemListByPaperId(@RequestParam String examId){
        return problemService.getProblemByExamId(examId);
    }
    @GetMapping("/problems/teacher")
    public Result<?> getProblemByTeacherId(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize){
        if(getUser().getUserType().equals("student"))
            return Result.error("-1","无权限");
        return problemService.getProblemByTeacherId(pageNum, pageSize, getAccountId());
    }

    @GetMapping("/problems/id/{problemId}")
    public Result<?> getProblemById(@PathVariable String problemId){
        if(getUser().getUserType().equals("student"))
            return Result.error("-1","无权限");
        return problemService.getProblemByProblemId(problemId);
    }

    @PostMapping("/problems/add")
    public Result<?> addProblem(@RequestBody Problem problem,
                                @RequestParam(defaultValue = "") String examId){
        problem.setCreatedDate(new Date());
        problem.setTeacherId(getAccountId());
        problem.setProblemId(IdUtil.fastSimpleUUID());
        Result<?> res = problemService.addProblem(problem);
        System.out.println(examId+" "+problem.getProblemId());
        if(res.getCode().equals("0") && !examId.equals(""))
            res = paperService.addProblemById(examId, problem.getProblemId());
        return res;
    }

    @PostMapping("/problems/update")
    public Result<?> updateProblem(@RequestParam Problem problem){
        if(!getAccountId().equals(problem.getTeacherId()))
            return Result.error("-1","无权限");
        return problemService.updateProblem(problem);
    }

    @PostMapping("/problems/delete")
    public Result<?> deleteProblem(@RequestParam String problemId){
        if(!getAccountId().equals(((Problem) problemService.getProblemByProblemId(problemId).getData()).getTeacherId()))
            return Result.error("-1","无权限");
        return problemService.deleteProblemByProblemId(problemId);
    }

    @PostMapping("/problems/deleteBatch")
    public Result<?> deleteProblems(@RequestParam List<String> ids){
        if(getUser().getUserType().equals("student"))
            return Result.error("-1","无权限");
        return problemService.deleteProblemByProblemId(ids);
    }

    @PostMapping("/problems/import")
    public Result<?> importProblem(HttpServletRequest request){
        MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
        return problemService.importProblemByExcel(getAccountId(), file);
    }

    //考卷相关
    @PostMapping("/paper/addProblem")
    public Result<?> addProblemToPaper(@RequestParam String examId, @RequestParam String problemId){
        if(!getAccountId().equals(examService.getTeacherIdByPaperId(examId).getData()))
            return Result.error("-1","无权限");
        return paperService.addProblemById(examId, problemId);
    }

    @PostMapping("/paper/addProblems")
    public Result<?> addProblemsToPaper(@RequestParam String examId, @RequestBody List<String> problemId){
        if(!getAccountId().equals(examService.getTeacherIdByPaperId(examId).getData()))
            return Result.error("-1","无权限");
        return paperService.addProblemById(examId, problemId);
    }

    @PostMapping("/paper/delete")
    public Result<?> deleteProblemById(@RequestParam String examId, @RequestParam String problemId){
        if(!getAccountId().equals(examService.getTeacherIdByPaperId(examId).getData()))
            return Result.error("-1","无权限");
        return paperService.deleteProblemById(examId, problemId);
    }

    @PostMapping("/paper/deleteProblems")
    public Result<?> deleteProblemsById(@RequestParam String examId, @RequestParam List<String> problemId){
        if(!getAccountId().equals(examService.getTeacherIdByPaperId(examId).getData()))
            return Result.error("-1","无权限");
        return paperService.deleteProblemById(examId, problemId);
    }

    @PostMapping("/paper/delete/{examId}")
    public Result<?> deletePaper(@PathVariable String examId){
        if(!getAccountId().equals(examService.getTeacherIdByPaperId(examId).getData()))
            return Result.error("-1","无权限");
        return paperService.clearPaperById(examId);
    }

    @GetMapping("/export/{examId}")
    public ResponseEntity<InputStreamResource> exportExam(@PathVariable String examId) throws IOException{
        Result result = paperService.exportPaperWithExcel(examId);
        if(result.getCode().equals("-1"))
            return null;
        MultipartFile file = (MultipartFile) result.getData();
        String prefix = "tmp/"+IdUtil.fastSimpleUUID();
        FileUtils fileUtils = new FileUtils();
        String path = fileUtils.upload(file, prefix);
        return fileUtils.downloadFile(prefix+file.getOriginalFilename());
    }

    //考试相关

    @GetMapping("/examList")
    public Result<?> getExamList(@RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize){
        return examService.getExamByUser(pageNum,pageSize,getAccountId());
    }

    @GetMapping("/id/{examId}")
    public Result<?> getExamByExamId(@PathVariable String examId){
        return examService.getExamById(examId);
    }

    @GetMapping("/all")
    public Result<?> getExams(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        if(getUser().getUserType().equals("student"))
            return Result.error("-1","无权限");
        return examService.getExamByUserId(pageNum, pageSize, getAccountId(), search);
    }

    @PostMapping("/add")
    public Result<?> addExam(@RequestBody Exam exam){
        System.out.println(exam);
        String examId = IdUtil.fastSimpleUUID();
        exam.setExamId(examId);
        exam.setCreatedTime(new Date());
        exam.setTeacherId(getAccountId());
        System.out.println("\n"+exam+"\n");
        return examService.addExam(exam);
    }

    @PostMapping("/update")
    public Result<?> updateExam(@RequestParam Exam exam){
        if(!exam.getTeacherId().equals(getAccountId()))
            return Result.error("-1","无权限");
        return examService.updateExam(exam);
    }

    @PostMapping("/delete/{examId}")
    public Result<?> deleteExam(@PathVariable String examId){
        if(!((Exam)examService.getExamById(examId).getData()).getTeacherId().equals(getAccountId()))
            return Result.error("-1","无权限");
        return examService.deleteExamById(examId);
    }

    //做考试相关
    @PostMapping("/handout")
    public Result<?> handOutExam(@RequestParam String examId){
        return examService.handOutExam(examId);
    }

    @GetMapping("/doExams/all")
    public Result<?> getDoExamByUserId(@RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize){
        return examService.getDoExamByUserId(pageNum, pageSize, getAccountId());
    }

    @GetMapping("/doExams/find/{examId}")
    public Result<?> getDoExamByExamId(@RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                       @PathVariable String examId){
        return examService.getDoExamByExamId(pageNum, pageSize, examId);
    }

    @GetMapping("/doExams/score")
    public Result<?> getScore(@RequestParam String examId){
        return examService.getScore(examId,getAccountId());
    }

    @PostMapping("/doExams/judgeScore")
    public Result<?> submitExam(@RequestParam String examId, @RequestBody List<String> answer){
        DoExam doExam = new DoExam();
        doExam.setExamId(examId);
        doExam.setId(getAccountId());
        return examService.scoreExam(doExam, answer);
    }
}

