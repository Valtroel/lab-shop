package com.example.labshop.repository;

import com.example.labshop.enumeration.OrderStatus;
import com.example.labshop.mapper.OrderMapper;
import com.example.labshop.model.OrderModel;
import com.example.labshop.model.PhotoModel;
import com.example.labshop.model.ProductModel;
import com.example.labshop.shop_db.tables.records.OrdersRecord;
import org.jooq.DSLContext;
import org.jooq.Record14;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.labshop.shop_db.Tables.*;

@Repository
public class DefaultOrderRepository implements OrderRepository {

    private final DSLContext dslContext;
    private final OrderMapper mapper;
    private final UserRepository userRepository;

    private static final int SUCCESS_INDICATOR = 1;

    public DefaultOrderRepository(DSLContext dslContext, OrderMapper mapper, UserRepository userRepository) {
        this.dslContext = dslContext;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public boolean saveOrder(OrderModel orderModel) {
        Long userId = userRepository.findUserByEmail(orderModel.getUserEmail()).getId();
        Double totalCost = orderModel.getProductModels().stream().map(ProductModel::getCost).mapToDouble(d -> d).sum();
        return orderModel
                .getProductModels()
                .stream()
                .map(product -> dslContext
                        .insertInto(ORDERS)
                        .set(ORDERS.USER_ID, userId)
                        .set(ORDERS.PRODUCT_ID, product.getId())
                        .set(ORDERS.ORDER_DATE, orderModel.getOrderDate())
                        .set(ORDERS.TOTAL_COST, totalCost)
                        .set(ORDERS.STATUS, orderModel.getStatus().name())
                        .set(ORDERS.UUID, orderModel.getUuid())
                        .execute())
                .distinct()
                .toList()
                .size() == SUCCESS_INDICATOR;
    }

    @Override
    public boolean updateOrder(OrderModel orderModel) {
        Long userId = userRepository.findUserByEmail(orderModel.getUserEmail()).getId();
        return mapper.toRecords(orderModel)
                .stream()
                .map(ordersRecord -> dslContext
                        .update(ORDERS)
                        .set(ORDERS.PRODUCT_ID, ordersRecord.getProductId())
                        .set(ORDERS.TOTAL_COST, ordersRecord.getTotalCost())
                        .where(ORDERS.USER_ID.eq(userId)
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
        Long userId = userRepository.findUserByEmail(orderModel.getUserEmail()).getId();
        return dslContext
                .deleteFrom(ORDERS)
                .where(ORDERS.USER_ID.eq(userId)
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
        return queryPart()
                .where(USERS.EMAIL.eq(email).and(ORDERS.STATUS.eq("PENDING")))
                .fetch(record -> new OrderModel(
                                record.value1(),
                                record.value2(),
                                record.value3(),
                                List.of(new ProductModel(
                                                record.value4(),
                                                record.value11(),
                                                record.value5(),
                                                record.value6(),
                                                record.value7(),
                                                new PhotoModel(
                                                                record.value8(),
                                                                record.value4(),
                                                                record.value9()
                                                        ),
                                        record.value13())
                                ),
                                record.value12(),
                                record.value10(),
                        OrderStatus.valueOf(record.value14()))
                );
    }

    @Override
    public List<OrderModel> findAllOrders() {
        return queryPart()
                .fetch(record -> new OrderModel(
                                record.value1(),
                                record.value2(),
                                record.value3(),
                                List.of(new ProductModel(
                                        record.value4(),
                                        record.value11(),
                                        record.value5(),
                                        record.value6(),
                                        record.value7(),
                                        new PhotoModel(
                                                record.value8(),
                                                record.value4(),
                                                record.value9()
                                        ),
                                        record.value13())
                                ),
                                record.value12(),
                                record.value10(),
                        OrderStatus.valueOf(record.value14()))
                );
    }

    @Override
    public OrderModel findOrderById(Long id) {
        return queryPart()
                .where(ORDERS.ID.eq(id))
                 .fetchOne(record -> new OrderModel(
                        record.value1(),
                        record.value2(),
                        record.value3(),
                        List.of(new ProductModel(
                                record.value4(),
                                record.value11(),
                                record.value5(),
                                record.value6(),
                                record.value7(),
                                new PhotoModel(
                                        record.value8(),
                                        record.value4(),
                                        record.value9()
                                ),
                                record.value13())
                        ),
                        record.value12(),
                        record.value10(),
                         OrderStatus.valueOf(record.value14())
                 )
        );
    }

    private SelectJoinStep<Record14<Long, String, LocalDateTime, Long, String, Double, LocalDateTime, Long, String, String, String, Double, String, String>> queryPart(){
        return dslContext
                .select(
                        ORDERS.ID,
                        USERS.EMAIL,
                        ORDERS.ORDER_DATE,
                        PRODUCTS.ID,
                        PRODUCTS.NAME,
                        PRODUCTS.COST,
                        PRODUCTS.POST_DATE,
                        PHOTOS.ID,
                        PHOTOS.PATH,
                        ORDERS.UUID,
                        PRODUCT_CATEGORY.NAME,
                        ORDERS.TOTAL_COST,
                        PRODUCTS.INFO,
                        ORDERS.STATUS
                )
                .from(ORDERS
                        .join(PRODUCTS)
                        .on(ORDERS.PRODUCT_ID.eq(PRODUCTS.ID))
                        .join(PHOTOS)
                        .on(ORDERS.PRODUCT_ID.eq(PHOTOS.PRODUCT_ID))
                        .join(USERS)
                        .on(ORDERS.USER_ID.eq(USERS.ID))
                        .join(PRODUCT_CATEGORY)
                        .on(PRODUCT_CATEGORY.ID.eq(PRODUCTS.CATEGORY_ID))
                );
    }

    private List<ProductModel> findOrderProducts(OrderModel orderModel){
        return dslContext
                .select(
                        PRODUCTS.ID,
                        PRODUCTS.NAME,
                        PRODUCTS.COST,
                        PRODUCTS.POST_DATE,
                        PHOTOS.ID,
                        PHOTOS.PATH,
                        PRODUCT_CATEGORY.NAME,
                        PRODUCTS.INFO
                )
                .from(PRODUCTS.join(PHOTOS)
                        .on(PHOTOS.PRODUCT_ID.eq(PRODUCTS.ID))
                        .join(PRODUCT_CATEGORY)
                        .on(PRODUCTS.CATEGORY_ID.eq(PRODUCT_CATEGORY.ID))

                )
                .where(ORDERS.UUID.eq(orderModel.getUuid()))
                .fetch(record -> new ProductModel(
                        record.value1(),
                        record.value7(),
                        record.value2(),
                        record.value3(),
                        record.value4(),
                        new PhotoModel(
                                record.value1(),
                                record.value5(),
                                record.value6()
                        ),
                        record.value8())
                );
    }
}
