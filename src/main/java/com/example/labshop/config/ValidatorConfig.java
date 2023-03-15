package com.example.labshop.config;

import com.example.labshop.service.validator.DefaultUserParamsValidator;
import com.example.labshop.service.validator.UserParamsValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfig {

    @Bean
    public UserParamsValidator userParamsValidator(){
        return new DefaultUserParamsValidator();
    }
}
