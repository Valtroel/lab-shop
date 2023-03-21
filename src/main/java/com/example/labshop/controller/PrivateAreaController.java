package com.example.labshop.controller;

import com.example.labshop.model.OrderModel;
import com.example.labshop.model.OrderSearchModel;
import com.example.labshop.model.UserModel;
import com.example.labshop.repository.OrderRepository;
import com.example.labshop.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller("/private-area")
public class PrivateAreaController {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public PrivateAreaController(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    @GetMapping("/private-area")
    public String privateArea(Model model){
        model.addAttribute("orderSearchModel", new OrderSearchModel());
        return "/private-area";
    }


    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    @GetMapping("/users")
    public String users(Model model){
        List<UserModel> userModels = userRepository.findAll();
        model.addAttribute("orderSearchModel", new OrderSearchModel());
        model.addAttribute("users", userModels);
        return "/private-area-users";
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    @GetMapping("/orders")
    public String orders(Model model){
        List<OrderModel> orderModels = orderRepository.findAllOrders();
        model.addAttribute("orderSearchModel", new OrderSearchModel());
        model.addAttribute("orders", orderModels);
        return "/private-area-orders";
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    @GetMapping("/search-order")
    public String searchOrder(@ModelAttribute OrderSearchModel orderSearchModel, Model model){
        OrderModel orderModel = orderRepository.findOrderById(Long.parseLong(orderSearchModel.getKeyword()));
        List<UserModel> userModels = userRepository.findAll();
        model.addAttribute("orderSearchModel", new OrderSearchModel());
        model.addAttribute("users", userModels);
        model.addAttribute("order", orderModel);
        return "/private-area-search-order";
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    @GetMapping("/change-user-role")
    public String changeUserRole(@ModelAttribute UserModel userModel, Model model){
        userRepository.updateUser(userModel);
        List<UserModel> userModels = userRepository.findAll();
        model.addAttribute("orderSearchModel", new OrderSearchModel());
        model.addAttribute("users", userModels);
        return "/private-area-users";
    }
}
