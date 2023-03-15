package com.example.labshop.mapper;

import com.example.labshop.model.OrderModel;
import com.example.labshop.shop_db.tables.records.OrdersRecord;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {


    public List<OrdersRecord> toRecords(OrderModel orderModel) {
        return orderModel.getProductModels()
                .stream()
                .map(productModel -> {
                    OrdersRecord record = new OrdersRecord();
                    record.setUserId(orderModel.getUserId());
                    record.setOrderDate(orderModel.getOrderDate());
                    record.setTotalCost(orderModel.getTotalCost());
                    record.setProductId(productModel.getId());
                    return record;
                })
                .collect(Collectors.toList());
    }


    public OrderModel toModel(List<OrdersRecord> ordersRecords) {

        return null;
    }
}
