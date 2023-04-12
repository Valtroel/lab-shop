package com.example.labshop.service;

import com.example.labshop.enumeration.AuthUserInfo;
import com.example.labshop.enumeration.UserRole;
import com.example.labshop.model.UserModel;
import com.example.labshop.repository.UserRepository;
import com.example.labshop.service.validator.UserParamsValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.labshop.enumeration.AuthUserInfo.*;

@Service
@Transactional
public class UserService{

    private static final Logger log = LogManager.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserParamsValidator userParamsValidator;

    public UserService(UserRepository userRepository, UserParamsValidator userParamsValidator) {
        this.userRepository = userRepository;
        this.userParamsValidator = userParamsValidator;
    }




    public boolean changeUserRole(UserModel userModel){
        return userRepository.updateUser(userModel);
    }

    public List<UserModel> getAllUsers(){
        return userRepository.findAll();
    }

    private boolean isUserExist(String email){
        return userRepository
                .findUserByEmail(email) != null;
    }
}
