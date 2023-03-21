package com.example.labshop.model;

import com.example.labshop.enumeration.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderModel {

    private Long id;
    private String userEmail;
    private LocalDateTime orderDate;
    private List<ProductModel> productModels;
    private Double totalCost;

    private String uuid;

    private OrderStatus status;


    public OrderModel(Long id, String userEmail, LocalDateTime orderDate, List<ProductModel> productModels, Double totalCost, String uuid, OrderStatus status) {
        this.id = id;
        this.userEmail = userEmail;
        this.orderDate = orderDate;
        this.productModels = productModels;
        this.totalCost = totalCost;
        this.uuid = uuid;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
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

    public String getUuid() {
        return uuid;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setProductModels(List<ProductModel> productModels) {
        this.productModels = productModels;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
