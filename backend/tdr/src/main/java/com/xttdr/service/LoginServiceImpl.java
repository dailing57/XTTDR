package com.xttdr.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xttdr.common.Result;
import com.xttdr.entity.Account;
import com.xttdr.entity.User;
import com.xttdr.mapper.AccountMapper;
import com.xttdr.mapper.UserMapper;
import com.xttdr.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService{
    @Resource
    AccountMapper accountMapper;
    @Resource
    UserMapper userMapper;
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
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id",account.getId());
        res.setUser(userMapper.selectOne(userQueryWrapper));
        // 生成token
        String token = TokenUtils.genToken(res);
        res.setToken(token);
        return Result.success(res);
    }

    @Override
    public Result<?> register(Account account) {
        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("id", account.getId());
        Account resAccount = accountMapper.selectOne(accountQueryWrapper);
        if(resAccount!=null){
            return Result.error("-1", "用户名重复");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("work_id",account.getUser().getWorkId());
        User resUser = userMapper.selectOne(userQueryWrapper);
        if(resUser==null){
            return Result.error("-1", "无效学号");
        }
        String workId = resUser.getWorkId();
        String role = "";
        if(workId.charAt(0)=='S'){
            role = "student";
        }
        else{
            role = "teacher";
        }
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("work_id",workId).set("id",account.getId());
        userMapper.update(null,userUpdateWrapper);
        account.setUserType(role);
        accountMapper.insert(account);
        return Result.success();
    }

    @Override
    public Result<?> getAccountById(String id) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Account res = accountMapper.selectOne(queryWrapper);
        if (res == null) {
            return Result.error("-1", "用户名或密码错误");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id",id);
        res.setUser(userMapper.selectOne(userQueryWrapper));
        // 生成token
        String token = TokenUtils.genToken(res);
        res.setToken(token);
        return Result.success(res);
    }


}
