package com.platform.account.controller;

import com.platform.account.handler.AjaxResult;
import com.platform.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;


    /**
     * 扣减库存接口
     * @param userId
     * @param price
     */
    @PostMapping("/reduceBalance")
    AjaxResult reduceBalance(@RequestParam("userId") Long userId, @RequestParam("price")Double price)
    {
        int res = accountService.reduceBalance(userId, price);

        return AjaxResult.success(res);
    }

}
