package com.poscodx.orderservice.service;

import com.poscodx.orderservice.dto.OrderDto;
import com.poscodx.orderservice.jpa.OrderEntity;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDetails);
    OrderDto getOrderByOrderId(String OrderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);
}
