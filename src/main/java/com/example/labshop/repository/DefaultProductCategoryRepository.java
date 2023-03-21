package com.example.labshop.repository;

import com.example.labshop.model.ProductCategoryModel;
import com.example.labshop.shop_db.tables.records.ProductCategoryRecord;
import org.jooq.DSLContext;

import java.util.List;

import static com.example.labshop.shop_db.Tables.PRODUCT_CATEGORY;

public class DefaultProductCategoryRepository implements ProductCategoryRepository{

    private final DSLContext dslContext;

    public DefaultProductCategoryRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Long saveCategory(ProductCategoryModel productCategoryModel) {
        return dslContext
                .insertInto(PRODUCT_CATEGORY)
                .set(PRODUCT_CATEGORY.ID, productCategoryModel.getId())
                .set(PRODUCT_CATEGORY.NAME, productCategoryModel.getCategoryName())
                .returningResult(PRODUCT_CATEGORY.ID)
                .fetchOneInto(Long.class);
    }

    @Override
    public Long updateCategory(ProductCategoryModel productCategoryModel) {
        return dslContext
                .update(PRODUCT_CATEGORY)
                .set(PRODUCT_CATEGORY.NAME, productCategoryModel.getCategoryName())
                .where(PRODUCT_CATEGORY.ID.eq(productCategoryModel.getId()))
                .returningResult(PRODUCT_CATEGORY.ID)
                .fetchOneInto(Long.class);
    }

    @Override
    public boolean deleteCategory(Long id) {
        return dslContext
                .deleteFrom(PRODUCT_CATEGORY)
                .where(PRODUCT_CATEGORY.ID.eq(id))
                .execute() == 1;
    }

    @Override
    public ProductCategoryModel findCategoryById(Long id) {
        return dslContext
                .select(
                        PRODUCT_CATEGORY.ID,
                        PRODUCT_CATEGORY.NAME
                )
                .from(PRODUCT_CATEGORY)
                .where(PRODUCT_CATEGORY.ID.eq(id))
                .fetchOne(record ->
                        new ProductCategoryModel(
                                record.value1(),
                                record.value2()
                        )
                );
    }

    @Override
    public List<ProductCategoryModel> findAllCategories() {
        return dslContext
                .select(
                        PRODUCT_CATEGORY.ID,
                        PRODUCT_CATEGORY.NAME
                )
                .from(PRODUCT_CATEGORY)
                .fetch(record ->
                    new ProductCategoryModel(
                            record.value1(),
                            record.value2()
                    )
                );
    }

    @Override
    public ProductCategoryModel findCategoryByName(String name) {
        ProductCategoryRecord record = dslContext
                .selectFrom(PRODUCT_CATEGORY)
                .where(PRODUCT_CATEGORY.NAME.eq(name))
                .fetchOneInto(ProductCategoryRecord.class);
        return record == null? null: new ProductCategoryModel(record.getId(), record.getName());
    }
}
