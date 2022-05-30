package com.platform.order.feign.service;


import com.platform.order.entity.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * author:HUAWEI
 */
@FeignClient(name = "product-seata")
public interface ProductFeignService {

    /**
     * 扣减库存
     *
     * @param productId 商品 ID
     * @param amount 扣减数量
     * @return 商品总价
     */
    @GetMapping(value = "/reduceStock")
    AjaxResult reduceStock(@RequestParam("productId")Long productId, @RequestParam("amount")Integer amount);
}
