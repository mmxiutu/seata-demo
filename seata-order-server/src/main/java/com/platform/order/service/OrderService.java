package com.platform.order.service;


import com.platform.order.dto.PlaceOrderRequest;

public interface OrderService
{
    /**
     * 下单
     *
     * @param placeOrderRequest 订单请求参数
     */
    void placeOrder(PlaceOrderRequest placeOrderRequest);
}