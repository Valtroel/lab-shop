package com.example.labshop.repository;

import com.example.labshop.model.PhotoModel;
import com.example.labshop.model.ProductModel;
import org.jooq.DSLContext;

import java.util.List;

import static com.example.labshop.shop_db.Tables.PHOTOS;

public class DefaultPhotoRepository implements PhotoRepository {

    private final DSLContext dslContext;

    private static final int SUCCESS_INDICATOR = 1;

    public DefaultPhotoRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public boolean savePhotos(ProductModel productModel, Long productId) {
        List<PhotoModel> photosPaths = productModel.getPhotos();
        List<Integer> resultsOfInsertion = photosPaths
                .stream()
                .map(path -> dslContext
                        .insertInto(PHOTOS)
                        .set(PHOTOS.PRODUCT_ID, productId)
                        .set(PHOTOS.PATH, path.getPath())
                        .execute()
                )
                .toList();
        return resultsOfInsertion.size() >= SUCCESS_INDICATOR;
    }

    @Override
    public boolean updatePhotos(ProductModel productModel) {
        List<PhotoModel> photosPaths = productModel.getPhotos();
        List<Integer> resultsOfInsertion = photosPaths
                .stream()
                .map(path -> dslContext
                        .update(PHOTOS)
                        .set(PHOTOS.PRODUCT_ID, productModel.getId())
                        .set(PHOTOS.PATH, path.getPath())
                        .where(PHOTOS.PRODUCT_ID.eq(productModel.getId()))
                        .execute()
                )
                .toList();
        return resultsOfInsertion.size() >= SUCCESS_INDICATOR;
    }

    @Override
    public boolean deletePhotos(PhotoModel photoModel) {
        return dslContext
                .deleteFrom(PHOTOS)
                .where(PHOTOS.ID.eq(photoModel.getId())
                        .or(PHOTOS.PATH.eq(photoModel.getPath())))
                .execute() >= SUCCESS_INDICATOR;
    }
}
