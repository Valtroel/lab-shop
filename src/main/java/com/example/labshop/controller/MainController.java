package com.example.labshop.controller;

import com.example.labshop.model.OrderModel;
import com.example.labshop.model.ProductModel;
import com.example.labshop.model.UserModel;
import com.example.labshop.repository.OrderRepository;
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

    public MainController(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @PreAuthorize("isAnonymous()&&isAuthenticated()")
    @GetMapping("/")
    public String index(@ModelAttribute UserModel userModel, Model model){
        List<ProductModel> productModels = productRepository.findAllProducts(10, 0);
        List<OrderModel> orderModels = orderRepository.findOrderByUserEmail(userModel.getEmail());
        model.addAttribute("products", productModels);
        model.addAttribute("orders", orderModels);
        model.addAttribute("user", userModel);
        return "index" ;
    }
}
