package com.sam.springbootmall.service;

import com.sam.springbootmall.dto.CreateOrderRequest;

public interface OrderService {
    Integer creatrOrder(Integer userId, CreateOrderRequest request);
}
