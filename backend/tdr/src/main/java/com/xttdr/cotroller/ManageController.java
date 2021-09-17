package com.xttdr.cotroller;

import com.xttdr.common.Result;
import com.xttdr.entity.School;
import com.xttdr.entity.User;
import com.xttdr.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/manage")
public class ManageController extends BaseController{
    @Resource(name = "UserServiceImpl")
    UserService userService;

    @GetMapping("/all")
    public Result<?> getAllUsers(@RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "6") Integer pageSize,
                                 @RequestParam(defaultValue = "") String searchName,
                                 @RequestParam(defaultValue = "") String searchWorkId){
        if(getUser().getUserType().equals("admin"))
            return userService.getUsers(pageNum, pageSize, searchName, searchWorkId);
        return Result.error("-1","无权限");
    }

    @PostMapping("/add")
    public Result<?> addUser(@RequestParam String schoolId, @RequestParam String workId, @RequestParam String name){
        if(!getUser().getUserType().equals("admin"))
            return Result.error("-1","无权限");
        User user = new User();
        user.setSchoolId(schoolId);
        user.setWorkId(workId);
        user.setName(name);
        return userService.addUser(user);
    }
    /*
    public Result<?> addUser(@RequestParam User user){
        if(!getUser().getUserType().equals("admin"))
            return Result.error("-1","无权限");
        return userService.addUser(user);
    }
    */

    @PostMapping("/delete")
    public Result<?> deleteUser(@RequestParam String id){
        if(!getUser().getUserType().equals("admin"))
            return Result.error("-1","无权限");
        return userService.deleteUserById(id);
        //return userService.deleteUserByWorkId(id)
    }

    @PostMapping("/import")
    public Result<?> importUser(HttpServletRequest request){
        if(!getUser().getUserType().equals("admin"))
            return Result.error("-1","无权限");
        MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
        return userService.importUserByExcel(file);
    }

    @PostMapping("/school/add")
    public Result<?> addSchool(School school){
        if(!getUser().getUserType().equals("admin"))
            return Result.error("-1","无权限");
        return userService.addSchool(school);
    }

    @PostMapping("/school/delete/{schoolId}")
    public Result<?> deleteSchool(@PathVariable String schoolId){
        if(!getUser().getUserType().equals("admin"))
            return Result.error("-1","无权限");
        return userService.deleteSchool(schoolId);
    }

    @GetMapping("/school/all")
    public Result<?> addSchool(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize){
        if(!getUser().getUserType().equals("admin"))
            return Result.error("-1","无权限");
        return userService.getSchool(pageNum,pageSize);
    }
}
