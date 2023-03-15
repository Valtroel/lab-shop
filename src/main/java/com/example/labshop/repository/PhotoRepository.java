package com.example.labshop.repository;

import com.example.labshop.model.PhotoModel;
import com.example.labshop.model.ProductModel;

public interface PhotoRepository {

    boolean savePhotos(ProductModel productModel, Long productId);

    boolean updatePhotos(ProductModel productModel);

    boolean deletePhotos(PhotoModel photoModel);
}
