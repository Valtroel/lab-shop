package com.example.labshop.model;

public class ProductCategoryModel {

    private Long id;

    private String categoryName;

    public ProductCategoryModel(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public ProductCategoryModel() {
    }

    public Long getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
