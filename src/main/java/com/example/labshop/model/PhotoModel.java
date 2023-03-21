package com.example.labshop.model;

public class PhotoModel {

    private Long id;
    private Long productId;
    private String path;

    public PhotoModel(Long id, Long productId, String path) {
        this.id = id;
        this.productId = productId;
        this.path = path;
    }

    public PhotoModel() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setPath(String path) {
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
