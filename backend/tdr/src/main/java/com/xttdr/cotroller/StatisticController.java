package com.xttdr.cotroller;

import com.xttdr.common.Result;
import com.xttdr.entity.Statistic;
import com.xttdr.service.CommentServiceImpl;
import com.xttdr.service.CourseServiceImpl;
import com.xttdr.service.HomeworkServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/statistic")
public class StatisticController extends BaseController{
    @Resource
    HomeworkServiceImpl homeworkService;
    @Resource
    CommentServiceImpl commentService;
    @Resource
    CourseServiceImpl courseService;
    @GetMapping
    public Result<?> studyStatistic(){
        String id = getAccountId();
        Double homeworkAverageScore = homeworkService.getAverageScore(id);
        Integer homeworkSubmitCount = homeworkService.getSubmitCount(id);
        Integer commentCount = commentService.commentCount(id);
        Integer courseCount = courseService.courseCount(id);
        Statistic statistic = new Statistic(homeworkAverageScore,homeworkAverageScore,homeworkSubmitCount,commentCount,courseCount);
        return Result.success(statistic);
    }
    @GetMapping("/homework")
    public Result<?> homework(){
        String id = getAccountId();
        Double perfect = homeworkService.getRate(id, 4.0);
        Double common = homeworkService.getRate(id,3.0);
        Double bad = homeworkService.getRate(id,0.0);
        Double sum = bad;
        bad -= common;
        common -= perfect;
        List<Double> list = new ArrayList<>();
        list.add(perfect/sum);
        list.add(common/sum);
        list.add(bad/sum);
        return Result.success(list);
    }
}
