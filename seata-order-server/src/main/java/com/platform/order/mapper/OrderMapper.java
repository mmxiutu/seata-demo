package com.platform.order.mapper;


import com.platform.order.domain.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    public void insert(Order order);

    public void updateById(Order order);
}
