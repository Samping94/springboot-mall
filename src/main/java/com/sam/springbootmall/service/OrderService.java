package com.sam.springbootmall.service;

import com.sam.springbootmall.dto.CreateOrderRequest;
import com.sam.springbootmall.dto.OrderQueryParams;
import com.sam.springbootmall.model.Order;

import java.util.List;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest request);

    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderQueryParams params);

    Integer countOrder(OrderQueryParams params);
}
