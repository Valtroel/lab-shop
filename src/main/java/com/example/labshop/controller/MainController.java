package com.example.labshop.controller;

import com.example.labshop.model.ProductRequestModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class MainController {

    @GetMapping(value = {"/"})
    public String index() {
        return "/index";
    }

    @GetMapping("/requisites")
    public String requisites(){
        return "requisites";
    }

    @PostMapping("/apply-product-request")
    public String productRequest(@ModelAttribute ProductRequestModel productRequestModel){
        return "/index";
    }
}
