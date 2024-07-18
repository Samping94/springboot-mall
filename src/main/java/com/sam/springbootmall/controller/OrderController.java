package com.sam.springbootmall.controller;

import com.sam.springbootmall.dto.CreateOrderRequest;
import com.sam.springbootmall.dto.OrderQueryParams;
import com.sam.springbootmall.model.Order;
import com.sam.springbootmall.service.OrderService;
import com.sam.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<Order> cerateOrder(@PathVariable Integer userId, @RequestBody @Valid CreateOrderRequest request) {
        Integer orderId = orderService.createOrder(userId, request);
        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(@PathVariable Integer userId
            , @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit
            , @RequestParam(defaultValue = "0") @Min(0) Integer offset) {
        OrderQueryParams params = new OrderQueryParams();
        params.setUserId(userId);
        params.setLimit(limit);
        params.setOffset(offset);

        // 取得訂單資訊
        List<Order> orderList = orderService.getOrders(params);
        // 取得訂單總數
        Integer count = orderService.countOrder(params);
        // 分頁
        Page<Order> page = new Page<>();
        page.setTotal(count);
        page.setLimit(limit);
        page.setOffset(offset);
        page.setResults(orderList);

        return ResponseEntity.ok(page);
    }

}
