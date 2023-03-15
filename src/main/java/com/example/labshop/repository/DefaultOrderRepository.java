package com.example.labshop.repository;

import com.example.labshop.mapper.OrderMapper;
import com.example.labshop.model.OrderModel;
import com.example.labshop.model.PhotoModel;
import com.example.labshop.model.ProductModel;
import com.example.labshop.shop_db.tables.records.OrdersRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.labshop.shop_db.Tables.*;

@Repository
public class DefaultOrderRepository implements OrderRepository {

    private final DSLContext dslContext;
    private final OrderMapper mapper;

    private static final int SUCCESS_INDICATOR = 1;

    public DefaultOrderRepository(DSLContext dslContext, OrderMapper mapper) {
        this.dslContext = dslContext;
        this.mapper = mapper;
    }

    @Override
    public boolean saveOrder(OrderModel orderModel) {
        return dslContext
                .insertInto(ORDERS)
                .values(mapper.toRecords(orderModel))
                .execute() >= SUCCESS_INDICATOR;
    }

    @Override
    public boolean updateOrder(OrderModel orderModel) {
        return mapper.toRecords(orderModel)
                .stream()
                .map(ordersRecord -> dslContext
                        .update(ORDERS)
                        .set(ORDERS.PRODUCT_ID, ordersRecord.getProductId())
                        .set(ORDERS.TOTAL_COST, ordersRecord.getTotalCost())
                        .where(ORDERS.USER_ID.eq(orderModel.getUserId())
                                .and(ORDERS.ORDER_DATE.eq(orderModel.getOrderDate()))
                        )
                        .execute() >= SUCCESS_INDICATOR
                )
                .distinct()
                .toList()
                .size() >= SUCCESS_INDICATOR;
    }

    @Override
    public boolean deleteOrder(OrderModel orderModel) {
        return dslContext
                .deleteFrom(ORDERS)
                .where(ORDERS.USER_ID.eq(orderModel.getUserId())
                        .and(ORDERS.ORDER_DATE.eq(orderModel.getOrderDate()))
                )
                .execute() >= SUCCESS_INDICATOR;
    }

    @Override
    public OrderModel findOrderByUserId(Long userId) {
        List<OrdersRecord> ordersRecord = dslContext
                .selectFrom(ORDERS)
                .where(ORDERS.USER_ID.eq(userId))
                .fetchInto(OrdersRecord.class);
        return mapper.toModel(ordersRecord);
    }

    @Override
    public List<OrderModel> findOrderByUserEmail(String email) {
        return dslContext
                .select(
                        ORDERS.ID,
                        ORDERS.USER_ID,
                        ORDERS.ORDER_DATE,
                        PRODUCTS.ID,
                        PRODUCTS.NAME,
                        PRODUCTS.COST,
                        PRODUCTS.POST_DATE,
                        PHOTOS.ID,
                        PHOTOS.PATH
                )
                .from(ORDERS
                        .join(PRODUCTS)
                        .on(ORDERS.PRODUCT_ID.eq(PRODUCTS.ID))
                        .join(PHOTOS)
                        .on(ORDERS.PRODUCT_ID.eq(PHOTOS.PRODUCT_ID))
                        .join(USERS)
                        .on(ORDERS.USER_ID.eq(USERS.ID))
                )
                .where(USERS.EMAIL.eq(email))
                .fetch(record -> new OrderModel(
                                record.value1(),
                                record.value2(),
                                record.value3(),
                                List.of(new ProductModel(
                                                record.value4(),
                                                record.value5(),
                                                record.value6(),
                                                record.value7(),
                                                List.of(
                                                        new PhotoModel(
                                                                record.value8(),
                                                                record.value4(),
                                                                record.value9()
                                                        )
                                                )
                                        )
                                )
                        )
                );
    }
}
