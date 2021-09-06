package com.xttdr.service;
import com.xttdr.common.Result;
import com.xttdr.entity.Account;

public interface LoginService {
    public Result<?> login(Account account);
    public Result<?> register(Account account);
    public Result<?> getAccountById(String id);
}
