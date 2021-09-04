package com.xttdr.cotroller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xttdr.common.Result;
import com.xttdr.entity.Account;
import com.xttdr.mapper.AccountMapper;
import com.xttdr.service.LoginServiceImpl;
import com.xttdr.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/login")
public class AdminController {
    @Resource
    LoginServiceImpl loginService;

    @PostMapping
    public Result<?> login(@RequestBody Account account){
        return loginService.login(account);
    }
}
