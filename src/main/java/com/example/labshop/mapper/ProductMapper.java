package com.example.labshop.mapper;

import com.example.labshop.model.ProductModel;
import com.example.labshop.shop_db.tables.records.ProductsRecord;

public class ProductMapper implements Mapper<ProductsRecord, ProductModel>{

    @Override
    public ProductsRecord toRecord(ProductModel productModel) {
        ProductsRecord record = new ProductsRecord();
        record.setName(productModel.getName());
        record.setCost(productModel.getCost());
        return record;
    }

    @Override
    public ProductModel toModel(ProductsRecord productsRecord) {
        return null;
    }
}
