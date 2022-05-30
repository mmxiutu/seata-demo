package com.platform.product.controller;

import com.platform.product.handler.AjaxResult;
import com.platform.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/reduceStock")
    AjaxResult reduceStock(@RequestParam("productId") Long productId, @RequestParam("amount")Integer amount)
    {
        Double remain = productService.reduceStock(productId, amount);
        return AjaxResult.success(remain);
    }

}
