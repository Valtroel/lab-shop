package com.example.labshop.controller;

import com.example.labshop.model.OrderModel;
import com.example.labshop.model.UserModel;
import com.example.labshop.repository.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/shopping-cart")
    public String shoppingCart(@ModelAttribute UserModel userModel, Model model){
        List<OrderModel> orderModels = orderRepository
                .findOrderByUserEmail(userModel.getEmail());
        model.addAttribute("user", userModel);
        model.addAttribute("orders", orderModels);
        return "/shopping-cart";
    }
}
