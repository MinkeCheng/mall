package com.hengxunda.springcloud.account.service.impl;

import com.hengxunda.springcloud.account.service.LoginService;
import com.hengxunda.springcloud.common.exception.ServiceException;
import com.hengxunda.springcloud.common.security.jwt.AccountJwt;
import com.hengxunda.springcloud.common.security.jwt.JwtUtils;
import com.hengxunda.springcloud.common.utils.FastJsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class LoginServiceImpl implements LoginService {

    @Value("${p.account}")
    private String account;

    @Value("${p.password}")
    private String password;

    @Value("${jwt.timeout}")
    private long timeout;

    @Override
    public AccountJwt login(final String account, final String password) {
        log.info("account = [{}], password = [{}]", account, password);
        if (this.account.equals(account) && this.password.equals(password)) {
            AccountJwt accountJwt = AccountJwt.builder().account(account).build();
            final String jwt = JwtUtils.createJwt(FastJsonUtils.toJSONString(accountJwt), timeout);
            accountJwt.setJwt(jwt);
            return accountJwt;
        }
        throw new ServiceException("用户名或密码错误");
    }

}
