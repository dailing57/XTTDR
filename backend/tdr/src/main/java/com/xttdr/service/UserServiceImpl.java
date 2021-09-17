package com.xttdr.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xttdr.common.Result;
import com.xttdr.entity.Problem;
import com.xttdr.entity.School;
import com.xttdr.entity.User;
import com.xttdr.mapper.SchoolMapper;
import com.xttdr.mapper.UserMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
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

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService{
    @Resource
    private UserMapper userMapper;
    @Resource
    SchoolMapper schoolMapper;
    @Override
    public Result<?> getUsers(Integer pageNum, Integer pageSize, String name, String id) {
        return Result.success(userMapper.getUsers(new Page<User>(pageNum, pageSize), name, id));
    }

    @Override
    public Result<?> addUser(User user) {
        if(userMapper.insert(user)>0)
            return Result.success();
        return Result.error("-1","插入失败");
    }

    @Override
    public Result<?> updateUser(User user) {
        if(userMapper.updateById(user) > 0)
            return Result.success();
        return Result.error("-1","更新失败");
    }

    @Override
    public Result<?> deleteUserById(String id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("id",id);
        if(userMapper.delete(queryWrapper) > 0)
            return Result.success();
        return Result.error("-1","删除失败");
    }

    @Override
    public Result<?> deleteUserByWorkId(String workId) {
        if(userMapper.deleteById(workId) > 0)
            return Result.success();
        return Result.error("-1","删除失败");
    }

    @Override
    public Result<?> importUserByExcel(MultipartFile file) {
        Workbook workbook;
        User user = new User();
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
            DataFormatter formatter = new DataFormatter();
            for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i++){
                Row row = sheet.getRow(i);
                user.setSchoolId(formatter.formatCellValue(row.getCell(1)));
                user.setWorkId(formatter.formatCellValue(row.getCell(2)));
                user.setName(formatter.formatCellValue(row.getCell(3)));
                if(userMapper.insert(user) <= 0)
                    return Result.error("-1","您导入的第"+i+"行信息存在问题");
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

    @Override
    public Result<?> addSchool(School school) {
        schoolMapper.insert(school);
        return Result.success();
    }

    @Override
    public Result<?> deleteSchool(String schoolId) {
        schoolMapper.deleteById(schoolId);
        return Result.success();
    }

    @Override
    public Result<?> getSchool(Integer pageNum, Integer pageSize) {
        return Result.success(schoolMapper.selectPage(new Page<School>(pageNum,pageSize),null));
    }
}
