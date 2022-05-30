package com.platform.account.service.impl;

import com.platform.account.domain.Account;
import com.platform.account.mapper.AccountMapper;
import com.platform.account.service.AccountService;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class AccountServiceImpl implements AccountService
{
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Resource
    private AccountMapper accountMapper;

    /**
     * 扣减账户余额
     * 事务传播特性设置为 REQUIRES_NEW 开启新的事务 重要！！！！一定要使用REQUIRES_NEW
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int reduceBalance(Long userId, Double price)
    {
        int result = 0;

        log.info("=============ACCOUNT START=================");
        log.info("当前 XID: {}", RootContext.getXID());

        Account account = accountMapper.selectById(userId);
        Double balance = account.getBalance();
        log.info("下单用户{}余额为 {},商品总价为{}", userId, balance, price);

        if (balance < price)
        {
            log.warn("用户 {} 余额不足，当前余额:{}", userId, balance);
            throw new RuntimeException("余额不足");
        }
        log.info("开始扣减用户 {} 余额", userId);
        double currentBalance = account.getBalance() - price;
        account.setBalance(currentBalance);
        result = accountMapper.updateById(account);
        log.info("扣减用户 {} 余额成功,扣减后用户账户余额为{}", userId, currentBalance);
        log.info("=============ACCOUNT END=================");

        return result;
    }

}
