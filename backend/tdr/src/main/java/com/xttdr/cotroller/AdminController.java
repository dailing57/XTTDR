package com.xttdr.cotroller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xttdr.common.Result;
import com.xttdr.entity.Account;
import com.xttdr.mapper.AccountMapper;
import com.xttdr.utils.TokenUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/login")
public class AdminController {
    @Resource
    AccountMapper accountMapper;

    @PostMapping
    public Result<?> login(@RequestBody Account account){
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", account.getId());
        queryWrapper.eq("pwd", account.getPwd());
        queryWrapper.eq("user_type", account.getUserType());
        Account res = accountMapper.selectOne(queryWrapper);
        if (res == null) {
            return Result.error("-1", "用户名或密码错误");
        }
        // 生成token
        String token = TokenUtils.genToken(res);
        res.setToken(token);
        return Result.success(res);
    }
}
