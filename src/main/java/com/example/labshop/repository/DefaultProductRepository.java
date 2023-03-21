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

import static com.example.labshop.shop_db.Tables.*;

@Repository
public class DefaultProductRepository implements ProductRepository {

    private final DSLContext dslContext;
    private final Mapper<ProductsRecord, ProductModel> mapper;

    private final ProductCategoryRepository productCategoryRepository;
    private final PhotoRepository photoRepository;

    private static final int SUCCESS_INDICATOR = 1;

    public DefaultProductRepository(DSLContext dslContext, Mapper<ProductsRecord, ProductModel> mapper, ProductCategoryRepository productCategoryRepository, PhotoRepository photoRepository) {
        this.dslContext = dslContext;
        this.mapper = mapper;
        this.productCategoryRepository = productCategoryRepository;
        this.photoRepository = photoRepository;
    }

    @Override
    public Long saveProduct(ProductModel productModel) {
        Long categoryId = productCategoryRepository.findCategoryByName(productModel.getCategory()).getId();
        Long productId =  Objects.requireNonNull(dslContext
                        .insertInto(PRODUCTS)
                        .set(PRODUCTS.NAME, productModel.getName())
                        .set(PRODUCTS.COST, productModel.getCost())
                        .set(PRODUCTS.POST_DATE, productModel.getPostDate())
                        .set(PRODUCTS.CATEGORY_ID, categoryId)
                        .set(PRODUCTS.INFO, productModel.getAdditionalInfo())
                        .returningResult(PRODUCTS.ID)
                        .fetchOne())
                .value1();
        photoRepository.savePhotos(productModel,productId);
        return productId;
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
                .where(PRODUCTS.ID.eq(productId))
                .fetchOne(record -> new ProductModel(
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

    @Override
    public List<ProductModel> findAllProducts(int limit, int offset) {
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
                .limit(limit)
                .offset(offset)
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
                        PHOTOS.PATH,
                        PRODUCT_CATEGORY.NAME
                )
                .from(PRODUCTS.join(PHOTOS)
                                .on(PHOTOS.PRODUCT_ID.eq(PRODUCTS.ID))
                                .join(PRODUCT_CATEGORY)
                                .on(PRODUCTS.CATEGORY_ID.eq(PRODUCT_CATEGORY.ID)))
                .where(PRODUCTS.COST.between(minCost, maxCost))
                .orderBy(sortCondition)
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
                        record.value7())
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
                        PHOTOS.PATH,
                        PRODUCT_CATEGORY.NAME,
                        PRODUCTS.INFO
                )
                .from(PRODUCTS.join(PHOTOS)
                                .on(PHOTOS.PRODUCT_ID.eq(PRODUCTS.ID))
                                .join(PRODUCT_CATEGORY)
                                .on(PRODUCTS.CATEGORY_ID.eq(PRODUCT_CATEGORY.ID))
                )
                .where()
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

    @Override
    public List<ProductModel> findProductsByCategory(String category) {
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
                .where(PRODUCT_CATEGORY.NAME.eq(category))
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
