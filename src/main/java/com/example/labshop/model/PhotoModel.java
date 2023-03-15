package com.example.labshop.model;

public class PhotoModel {

    private final Long id;
    private final Long productId;
    private final String path;

    public PhotoModel(Long id, Long productId, String path) {
        this.id = id;
        this.productId = productId;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getPath() {
        return path;
    }
}
