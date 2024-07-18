package com.sam.springbootmall.service.impl;

import com.sam.springbootmall.dao.OrderDao;
import com.sam.springbootmall.dao.ProductDao;
import com.sam.springbootmall.dao.UserDao;
import com.sam.springbootmall.dto.BuyItem;
import com.sam.springbootmall.dto.CreateOrderRequest;
import com.sam.springbootmall.model.Order;
import com.sam.springbootmall.model.OrderItem;
import com.sam.springbootmall.model.Product;
import com.sam.springbootmall.model.User;
import com.sam.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class OrderServiceImpl implements OrderService {
    private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    @Override
    @Transactional
    public Integer createOrder(Integer userId, CreateOrderRequest request) {
        // 檢查user是否存在
        User user = userDao.getUserById(userId);
        if (Objects.isNull(user)) {
            logger.error("查無userId : {} 的使用者 !", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        for (BuyItem buyItem : request.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());
            // 計算總額
            if (Objects.isNull(product)) {
                logger.error("查無商品編號為: {} 的商品 !", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                logger.error("編號為: {} 的商品庫存不足 !", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 扣除商品庫存
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

            // 計算總額
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            // 轉換
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);
        }

        // 建立訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);
        List<OrderItem> orderItemList = orderDao.getOrderItemsById(orderId);
        order.setOrderItems(orderItemList);

        return order;
    }
}
