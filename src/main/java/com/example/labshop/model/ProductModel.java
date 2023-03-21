package com.example.labshop.model;

import java.time.LocalDateTime;

public class ProductModel {
    private Long id;
    private String category;
    private String name;
    private Double cost;
    private LocalDateTime postDate;
    private PhotoModel photos;

    private String additionalInfo;

    public ProductModel(Long id, String category, String name, Double cost, LocalDateTime postDate, PhotoModel photos, String additionalInfo) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.cost = cost;
        this.postDate = postDate;
        this.photos = photos;
        this.additionalInfo = additionalInfo;
    }

    public ProductModel() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }

    public void setPhotos(PhotoModel photos) {
        this.photos = photos;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getCost() {
        return cost;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    public PhotoModel getPhoto() {
        return photos;
    }

    public String getCategory() {
        return category;
    }
}
