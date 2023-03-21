package com.example.labshop.repository;

import com.example.labshop.model.ProductModel;

import java.util.List;

public interface ProductRepository {

    Long saveProduct(ProductModel productModel);
    boolean updateProduct(ProductModel productModel);
    boolean deleteProduct(ProductModel productModel);
    ProductModel findProductById(Long productId);

    List<ProductModel> findAllProducts(int limit, int offset);

    List<ProductModel> findProductsByCost(Double minCost, Double maxCost, Boolean firstNewProducts);

    List<ProductModel> findProductsByCategory(String category);

    List<ProductModel> findProductsByUserEmail(String email);

}
