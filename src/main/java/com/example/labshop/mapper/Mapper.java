package com.example.labshop.mapper;

public interface Mapper<T, V>{

    T toRecord(V v);
    V toModel(T t);
}
