package com.xttdr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xttdr.common.Result;
import com.xttdr.entity.Account;
import com.xttdr.mapper.AccountMapper;
import com.xttdr.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService{
    @Resource
    AccountMapper accountMapper;
    @Override
    public Result<?> login(Account account) {
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

    @Override
    public Result<?> register(Account account) {
        return null;
    }
}
