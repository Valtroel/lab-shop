package com.example.labshop.model;

import java.time.LocalDateTime;
import java.util.List;

public class OrderModel {

    private final Long id;
    private final Long userId;
    private final LocalDateTime orderDate;
    private final List<ProductModel> productModels;
    private final Double totalCost;

    public OrderModel(Long id,
                      Long userId,
                      LocalDateTime orderDate,
                      List<ProductModel> productModels) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.productModels = productModels;
        this.totalCost = productModels
                .stream()
                .map(ProductModel::getCost)
                .mapToDouble(cost -> cost)
                .sum();
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public List<ProductModel> getProductModels() {
        return productModels;
    }

    public Double getTotalCost() {
        return totalCost;
    }


}
