package com.example.labshop.controller;


import com.example.labshop.model.UserModel;
import com.example.labshop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody UserModel userModel, Model model){
        return ResponseEntity
                .ok()
                .body(userService.createUser(userModel).getMessage());
    }


    @PostMapping("/changeRole")
    public ResponseEntity<Boolean> changeUserRole(@RequestParam String userId){
        return ResponseEntity
                .ok()
                .body(true);
    }

    @GetMapping("/users")
    public String getUsers(Model model){
        List<UserModel> users = userService.getAllUsers();
        return "/";
    }
}
