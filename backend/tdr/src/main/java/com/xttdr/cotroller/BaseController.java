package com.xttdr.cotroller;

import com.auth0.jwt.JWT;
import com.xttdr.entity.Account;
import com.xttdr.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BaseController {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private HttpServletRequest request;

    public Account getUser() {
        String token = request.getHeader("token");
        String aud = JWT.decode(token).getAudience().get(0);
        return accountMapper.selectById(aud);
    }

    public String getAccountId(){
        String token = request.getHeader("token");
        String aud = JWT.decode(token).getAudience().get(0);
        return aud;
    }
}
