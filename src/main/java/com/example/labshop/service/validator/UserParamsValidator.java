package com.example.labshop.service.validator;

import com.example.labshop.model.UserModel;

public interface UserParamsValidator {

    boolean validateEmail(UserModel userModel);

    boolean validatePassword(UserModel userModel);
}
