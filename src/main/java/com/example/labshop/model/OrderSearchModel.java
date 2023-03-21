package com.example.labshop.model;

public class OrderSearchModel {

    private String keyword;

    public OrderSearchModel(String keyword) {
        this.keyword = keyword;
    }

    public OrderSearchModel() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
