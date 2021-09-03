package com.xttdr.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.xttdr.entity.Account;
import com.xttdr.mapper.AccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Component
public class TokenUtils {
    @Autowired
    private AccountMapper accountMapper;
    private static AccountMapper staticAccountMapper;

    @PostConstruct
    public void init() {
        staticAccountMapper = accountMapper;
    }

    //生成token
    public static String genToken(Account account) {
        return JWT.create().withExpiresAt(DateUtil.offsetDay(new Date(), 1)).withAudience(account.getId().toString())
                .sign(Algorithm.HMAC256(account.getPwd()));
    }

    //获取token中的用户信息
    public static Account getAccount() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            String aud = JWT.decode(token).getAudience().get(0);
            Integer accountId = Integer.valueOf(aud);
            return staticAccountMapper.selectById(accountId);
        } catch (Exception e) {
            log.error("解析token失败", e);
            return null;
        }
    }
}
