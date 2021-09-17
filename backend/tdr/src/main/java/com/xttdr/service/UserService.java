package com.xttdr.service;

import com.xttdr.common.Result;
import com.xttdr.entity.School;
import com.xttdr.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    Result<?> getUsers(Integer pageNum, Integer pageSize, String name, String id);
    Result<?> addUser(User user);
    Result<?> updateUser(User user);
    Result<?> deleteUserByWorkId(String workId);
    Result<?> deleteUserById(String id);
    Result<?> importUserByExcel(MultipartFile file);
    Result<?> addSchool(School school);
    Result<?> deleteSchool(String schoolId);
    Result<?> getSchool(Integer pageNum, Integer pageSize);
}
