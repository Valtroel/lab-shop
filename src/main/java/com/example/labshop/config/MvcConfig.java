package com.example.labshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/register").setViewName("reg");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/shopping-cart").setViewName("shopping-cart");
        registry.addViewController("/products").setViewName("products");
        registry.addViewController("/product-info").setViewName("card");
        registry.addViewController("/create-product-form").setViewName("create-product-form");
        registry.addViewController("/private-area").setViewName("private-area");
        registry.addViewController("/private-area-users").setViewName("private-area-users");
        registry.addViewController("/private-area-orders").setViewName("private-area-orders");
        registry.addViewController("/private-area-search-order").setViewName("private-area-search-order");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("file:src/main/resources/");
        registry.addResourceHandler("/css/**").addResourceLocations("file:src/main/resources/templates/css/");
        registry.addResourceHandler("/assets/**").addResourceLocations("file:src/main/resources/templates/assets/");
        registry.addResourceHandler("/js/**").addResourceLocations("file:src/main/resources/templates/js/");
        registry.addResourceHandler("/*.html").addResourceLocations("file:src/main/resources/templates/");
        registry.addResourceHandler("/resources/**").addResourceLocations("file:src/main/resources/");
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:src/main/resources/templates/uploads/")
                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());

    }

}
