package com.example.labshop.controller;

import com.example.labshop.enumeration.AuthUserInfo;
import com.example.labshop.model.ProductCategoryModel;
import com.example.labshop.model.UserModel;
import com.example.labshop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registration(Model model){
        model.addAttribute("user", new UserModel());
        return "/reg";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new UserModel());
        return "/login";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserModel user,
                               BindingResult result,
                               Model model){
        AuthUserInfo creationStatus = userService.createUser(user);

        if (!creationStatus.getMessage().equals(AuthUserInfo.SUCCESS.getMessage())){
            result.rejectValue(creationStatus.getType(), "400", creationStatus.getMessage());
        }

        if (result.hasErrors()){
            model.addAttribute("user", user);
            return "/reg";
        }

        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserModel user,
                               BindingResult result,
                               Model model){
        AuthUserInfo authStatus = userService.verifyUserLoginData(user);

        if (!authStatus.getMessage().equals(AuthUserInfo.SUCCESS.getMessage())){
            result.rejectValue(authStatus.getType(), "400", authStatus.getMessage());
        }

        if (result.hasErrors()){
            model.addAttribute("user", user);
            return "/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("addCategory", new ProductCategoryModel());
        return "redirect:/index";
    }

    @GetMapping("/activate-email/{code}")
    public String activateEmail(@PathVariable String code){
        return "/index";
    }
}
