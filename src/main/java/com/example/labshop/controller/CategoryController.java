package com.example.labshop.controller;


import com.example.labshop.model.ProductCategoryModel;
import com.example.labshop.repository.ProductCategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoryController {

    private final ProductCategoryRepository productCategoryRepository;

    public CategoryController(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @PreAuthorize("hasAuthority('isAuthenticated() && ADMIN')")
    @PostMapping("/add-category")
    public String addCategory(@ModelAttribute ProductCategoryModel productCategoryModel, Model model){
        productCategoryRepository.saveCategory(productCategoryModel);
        List<ProductCategoryModel> productCategoryModels = productCategoryRepository.findAllCategories();
        model.addAttribute("categories", productCategoryModels);
        return "/index";
    }

    @PreAuthorize("hasAuthority('isAuthenticated() && ADMIN')")
    @PostMapping("/delete-category/{categoryId}")
    public String deleteCategory(@PathVariable String categoryId, Model model){
        productCategoryRepository.deleteCategory(Long.parseLong(categoryId));
        List<ProductCategoryModel> productCategoryModels = productCategoryRepository.findAllCategories();
        model.addAttribute("categories", productCategoryModels);
        return "/index";
    }
}
