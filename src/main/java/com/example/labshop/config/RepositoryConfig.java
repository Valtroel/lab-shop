package com.example.labshop.config;

import com.example.labshop.mapper.OrderMapper;
import com.example.labshop.mapper.ProductMapper;
import com.example.labshop.mapper.UserMapper;
import com.example.labshop.repository.*;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    private final DSLContext dslContext;

    public RepositoryConfig(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Bean
    public UserRepository userRepository(){
        return new DefaultUserRepository(dslContext, new UserMapper());
    }

    @Bean
    public ProductRepository productRepository(){
        return new DefaultProductRepository(dslContext, new ProductMapper());
    }

    @Bean
    public OrderRepository orderRepository(){
        return new DefaultOrderRepository(dslContext, new OrderMapper());
    }

    @Bean
    public PhotoRepository photoRepository(){
        return new DefaultPhotoRepository(dslContext);
    }
}
