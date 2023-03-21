package com.example.labshop.model;

public class SearchModel {

    private String keyword;
    private Double minCost;
    private Double maxCost;
    private Boolean isNew;

    public SearchModel(String keyword, Double minCost, Double maxCost, Boolean isNew) {
        this.keyword = keyword;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.isNew = isNew;
    }

    public SearchModel() {

    }


    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setMinCost(Double minCost) {
        this.minCost = minCost;
    }

    public void setMaxCost(Double maxCost) {
        this.maxCost = maxCost;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public String getKeyword() {
        return keyword;
    }

    public Double getMinCost() {
        return minCost;
    }

    public Double getMaxCost() {
        return maxCost;
    }

    public Boolean getNew() {
        return isNew;
    }
}
