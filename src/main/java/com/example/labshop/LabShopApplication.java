package com.example.labshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class LabShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabShopApplication.class, args);
    }

}
