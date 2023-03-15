package com.example.labshop.repository;

import com.example.labshop.mapper.Mapper;
import com.example.labshop.model.PhotoModel;
import com.example.labshop.model.ProductModel;
import com.example.labshop.shop_db.tables.records.ProductsRecord;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.example.labshop.shop_db.Tables.PHOTOS;
import static com.example.labshop.shop_db.Tables.PRODUCTS;

@Repository
public class DefaultProductRepository implements ProductRepository {

    private final DSLContext dslContext;
    private final Mapper<ProductsRecord, ProductModel> mapper;

    private static final int SUCCESS_INDICATOR = 1;

    public DefaultProductRepository(DSLContext dslContext, Mapper<ProductsRecord, ProductModel> mapper) {
        this.dslContext = dslContext;
        this.mapper = mapper;
    }

    @Override
    public Long saveProduct(ProductModel productModel) {
        return Objects.requireNonNull(dslContext
                        .insertInto(PRODUCTS)
                        .set(mapper.toRecord(productModel))
                        .returningResult(PRODUCTS.ID)
                        .fetchOne())
                .value1();
    }

    @Override
    public boolean updateProduct(ProductModel productModel) {
        return dslContext
                .update(PRODUCTS)
                .set(PRODUCTS.NAME, productModel.getName())
                .set(PRODUCTS.COST, productModel.getCost())
                .where(PRODUCTS.ID.eq(productModel.getId()))
                .execute() == SUCCESS_INDICATOR;
    }

    @Override
    public boolean deleteProduct(ProductModel productModel) {
        return dslContext
                .deleteFrom(PRODUCTS)
                .where(PRODUCTS.ID.eq(productModel.getId()))
                .execute() == SUCCESS_INDICATOR;
    }

    @Override
    public ProductModel findProductById(Long productId) {
        ProductsRecord record = dslContext
                .selectFrom(PRODUCTS)
                .where(PRODUCTS.ID.eq(productId))
                .fetchOneInto(ProductsRecord.class);
        return mapper.toModel(record);
    }

    @Override
    public List<ProductModel> findAllProducts(int limit, int offset) {
        return dslContext
                .select(
                        PRODUCTS.ID,
                        PRODUCTS.NAME,
                        PRODUCTS.COST,
                        PRODUCTS.POST_DATE,
                        PHOTOS.ID,
                        PHOTOS.PATH
                )
                .from(PRODUCTS.join(PHOTOS).on(PHOTOS.PRODUCT_ID.eq(PRODUCTS.ID)))
                .limit(limit)
                .offset(offset)
                .fetch(record -> new ProductModel(
                                record.value1(),
                                record.value2(),
                                record.value3(),
                                record.value4(),
                                List.of(new PhotoModel(
                                        record.value1(),
                                        record.value5(),
                                        record.value6()
                                ))
                        )
                );
    }

    @Override
    public List<ProductModel> findProductsByCost(Double minCost, Double maxCost, Boolean firstNewProducts) {
        SortField<LocalDateTime> sortCondition =
                firstNewProducts ? PRODUCTS.POST_DATE.desc() : PRODUCTS.POST_DATE.asc();
        return dslContext
                .select(
                        PRODUCTS.ID,
                        PRODUCTS.NAME,
                        PRODUCTS.COST,
                        PRODUCTS.POST_DATE,
                        PHOTOS.ID,
                        PHOTOS.PATH
                )
                .from(PRODUCTS.join(PHOTOS).on(PHOTOS.PRODUCT_ID.eq(PRODUCTS.ID)))
                .where(PRODUCTS.COST.between(minCost, maxCost))
                .orderBy(sortCondition)
                .fetch(record -> new ProductModel(
                                record.value1(),
                                record.value2(),
                                record.value3(),
                                record.value4(),
                                List.of(new PhotoModel(
                                        record.value1(),
                                        record.value5(),
                                        record.value6()
                                ))
                        )
                );
    }

    @Override
    public List<ProductModel> findProductsByUserEmail(String email) {
        return dslContext
                .select(
                        PRODUCTS.ID,
                        PRODUCTS.NAME,
                        PRODUCTS.COST,
                        PRODUCTS.POST_DATE,
                        PHOTOS.ID,
                        PHOTOS.PATH
                )
                .from(PRODUCTS.join(PHOTOS).on(PHOTOS.PRODUCT_ID.eq(PRODUCTS.ID)))
                .where()
                .fetch(record -> new ProductModel(
                                record.value1(),
                                record.value2(),
                                record.value3(),
                                record.value4(),
                                List.of(new PhotoModel(
                                        record.value1(),
                                        record.value5(),
                                        record.value6()
                                ))
                        )
                );
    }
}
