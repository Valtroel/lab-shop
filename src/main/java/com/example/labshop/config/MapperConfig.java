package com.example.labshop.config;

import com.example.labshop.mapper.Mapper;
import com.example.labshop.mapper.OrderMapper;
import com.example.labshop.mapper.ProductMapper;
import com.example.labshop.mapper.UserMapper;
import com.example.labshop.model.ProductModel;
import com.example.labshop.model.UserModel;
import com.example.labshop.shop_db.tables.records.ProductsRecord;
import com.example.labshop.shop_db.tables.records.UsersRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public Mapper<UsersRecord, UserModel> userMapper(){
        return new UserMapper();
    }

    @Bean
    public Mapper<ProductsRecord, ProductModel> productMapper(){
        return new ProductMapper();
    }

    @Bean
    public OrderMapper orderMapper(){
        return new OrderMapper();
    }
}
