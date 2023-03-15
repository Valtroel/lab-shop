package com.example.labshop.service;

import com.example.labshop.model.PhotoModel;
import com.example.labshop.model.ProductModel;
import com.example.labshop.repository.PhotoRepository;
import com.example.labshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final PhotoRepository photoRepository;

    public ProductService(ProductRepository productRepository, PhotoRepository photoRepository) {
        this.productRepository = productRepository;
        this.photoRepository = photoRepository;
    }

    public List<ProductModel> searchProductsByCostBorders(Double minCost,
                                                          Double maxCost,
                                                          Boolean firstNewProducts){
        return productRepository.findProductsByCost(minCost, maxCost, firstNewProducts);
    }

    public boolean createProduct(ProductModel productModel){
        Long createdProductId = productRepository.saveProduct(productModel);
        return photoRepository.savePhotos(productModel, createdProductId);
    }

    public boolean updateProduct(ProductModel productModel){
        return productRepository.updateProduct(productModel)
                && photoRepository.updatePhotos(productModel);
    }

    public boolean deleteProductPhoto(PhotoModel photoModel){
        return true;
    }
}
