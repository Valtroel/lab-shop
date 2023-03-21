package com.example.labshop.repository;

import com.example.labshop.model.OrderModel;

import java.util.List;

public interface OrderRepository {

    boolean saveOrder(OrderModel orderModel);
    boolean updateOrder(OrderModel orderModel);
    boolean deleteOrder(OrderModel orderModel);
    OrderModel findOrderByUserId(Long userId);

    OrderModel findOrderById(Long id);

    List<OrderModel> findAllOrders();
    List<OrderModel> findOrderByUserEmail(String email);
}
