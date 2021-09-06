package com.xttdr.cotroller;

import com.xttdr.common.Result;
import com.xttdr.entity.Account;
import com.xttdr.service.LoginServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class LoginController {
    @Resource
    LoginServiceImpl loginService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody Account account){
        return loginService.login(account);
    }
    @PostMapping("/register")
    public Result<?> register(@RequestBody Account account){
        return loginService.register(account);
    }

    @GetMapping("/account/{id}")
    public Result<?> getAccountById(@PathVariable String id){
        return loginService.getAccountById(id);
    }

}
