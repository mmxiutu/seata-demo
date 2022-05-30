package com.platform.order.service.impl;


import com.platform.order.domain.Order;
import com.platform.order.dto.PlaceOrderRequest;
import com.platform.order.entity.AjaxResult;
import com.platform.order.feign.service.AccountFeignService;
import com.platform.order.feign.service.ProductFeignService;
import com.platform.order.mapper.OrderMapper;
import com.platform.order.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class OrderServiceImpl implements OrderService
{
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private AccountFeignService accountService;

    @Autowired
    private ProductFeignService productService;

    // @DS("order") // 每一层都需要使用多数据源注解切换所选择的数据库
    @Override
    @GlobalTransactional // 重点 第一个开启事务的需要添加seata全局事务注解
    public void placeOrder(PlaceOrderRequest request)
    {
        log.info("=============ORDER START=================");
        Long userId = request.getUserId();
        Long productId = request.getProductId();
        Integer amount = request.getAmount();
//        log.info("收到下单请求,用户:{}, 商品:{},数量:{}", userId, productId, amount);
//
//        log.info("当前 XID: {}", RootContext.getXID());
//
        Order order = new Order(userId, productId, 0, amount);
//
//        orderMapper.insert(order);
//        log.info("订单一阶段生成，等待扣库存付款中");
        // 扣减库存并计算总价

        commitOrder(request);

        AjaxResult result = productService.reduceStock(productId, amount);

        Double totalPrice = 0d;

        if(0 == (Integer) result.get(AjaxResult.CODE_TAG))
        {
            totalPrice = (Double)result.get("data");
        }
        else
        {
            throw new RuntimeException("扣减库存Failed!");
        }

        // 扣减余额
        result = accountService.reduceBalance(userId, totalPrice);

        // success
        if(0 == (Integer) result.get(AjaxResult.CODE_TAG))
        {
            order.setStatus(1);
            order.setTotalPrice(totalPrice);
            orderMapper.updateById(order);
            log.info("订单已成功下单");
        }
        else {
            throw new RuntimeException("扣减Account Failed!");
        }

        log.info("=============ORDER END=================");
    }

    @Transactional
    public void commitOrder(PlaceOrderRequest request)
    {
        log.info("=============ORDER START=================");
        Long userId = request.getUserId();
        Long productId = request.getProductId();
        Integer amount = request.getAmount();
        log.info("收到下单请求,用户:{}, 商品:{},数量:{}", userId, productId, amount);

        log.info("当前 XID: {}", RootContext.getXID());

        Order order = new Order(userId, productId, 0, amount);

        orderMapper.insert(order);
        log.info("订单一阶段生成，等待扣库存付款中");
    }

}
