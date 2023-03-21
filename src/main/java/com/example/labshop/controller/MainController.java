package com.example.labshop.controller;

import com.example.labshop.model.*;
import com.example.labshop.repository.OrderRepository;
import com.example.labshop.repository.ProductCategoryRepository;
import com.example.labshop.repository.ProductRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class MainController {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    private final ProductCategoryRepository productCategoryRepository;

    public MainController(ProductRepository productRepository, OrderRepository orderRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @PreAuthorize("isAnonymous()&&isAuthenticated()")
    @GetMapping(value = {"/"})
    public String index(@ModelAttribute UserModel userModel, Model model) {
        List<ProductModel> productModels = productRepository.findAllProducts(10, 0);
        List<ProductCategoryModel> productCategoryModels = productCategoryRepository.findAllCategories();
        List<OrderModel> orderModels = orderRepository.findOrderByUserEmail(userModel.getEmail());
        model.addAttribute("products", productModels);
        model.addAttribute("orders", orderModels);
        model.addAttribute("user", userModel);
        model.addAttribute("searchModel", new SearchModel());
        model.addAttribute("categories", productCategoryModels);
        model.addAttribute("addCategory", new ProductCategoryModel());
        return "/index";
    }


    @PreAuthorize("isAnonymous()&&isAuthenticated()")
    @GetMapping(value = {"/search"})
    public String search(@ModelAttribute UserModel userModel, Model model, @ModelAttribute("searchModel") SearchModel searchModel) {
        List<ProductModel> productModels;
        if (searchModel != null) {
            productModels =
                    productRepository.findProductsByCost(searchModel.getMinCost(),
                            searchModel.getMaxCost(),
                            searchModel.getNew());
        } else {
            productModels = productRepository.findAllProducts(10, 0);
        }
        List<ProductCategoryModel> productCategoryModels = productCategoryRepository.findAllCategories();
        List<OrderModel> orderModels = orderRepository.findOrderByUserEmail(userModel.getEmail());
        model.addAttribute("products", productModels);
        model.addAttribute("orders", orderModels);
        model.addAttribute("categories", productCategoryModels);
        model.addAttribute("user", userModel);
        model.addAttribute("addCategory", new ProductCategoryModel());
        return "/index";
    }
}
