package com.example.labshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/uploads/**").addResourceLocations("classpath:/resources/templates/uploads/");
        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/*.html").addResourceLocations("classpath:/static/templates/")
                .setCachePeriod(0);
    }

}
