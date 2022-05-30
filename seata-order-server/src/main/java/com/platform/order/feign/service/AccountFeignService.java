package com.platform.order.feign.service;

import com.platform.order.entity.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * author:HUAWEI
 */
@FeignClient(name = "account-seata")
public interface AccountFeignService {

    /**
     * 账户扣减
     * @param userId 用户 ID
     * @param price 扣减金额
     */
    @PostMapping(value = "/reduceBalance")
    AjaxResult reduceBalance(@RequestParam("userId")Long userId, @RequestParam("price")Double price);
}
