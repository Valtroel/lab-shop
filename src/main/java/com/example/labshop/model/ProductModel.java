package com.example.labshop.model;

import java.time.LocalDateTime;
import java.util.List;

public class ProductModel {
    private final Long id;
    private final String name;
    private Double cost;

    private final LocalDateTime postDate;
    private final List<PhotoModel> photos;

    public ProductModel(Long id, String name, Double cost, LocalDateTime postDate, List<PhotoModel> photos) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.postDate = postDate;
        this.photos = photos;
    }

    public void setCost(Double cost) {
        this.cost = cost;
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

    public List<PhotoModel> getPhotos() {
        return photos;
    }

}
