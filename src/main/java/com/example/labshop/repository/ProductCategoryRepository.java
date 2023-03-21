package com.example.labshop.repository;

import com.example.labshop.model.ProductCategoryModel;

import java.util.List;

public interface ProductCategoryRepository {

    Long saveCategory(ProductCategoryModel productCategoryModel);
    Long updateCategory(ProductCategoryModel productCategoryModel);

    boolean deleteCategory(Long id);

    ProductCategoryModel findCategoryById(Long id);
    ProductCategoryModel findCategoryByName(String name);

    List<ProductCategoryModel> findAllCategories();
}
