package com.example.labshop.service.validator;

import com.example.labshop.model.UserModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultUserParamsValidator implements UserParamsValidator {

    private static final String EMAIL_VALIDATION_REGEX =
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    private static final String PASSWORD_VALIDATION_REGEX =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{7,20})";
    @Override
    public boolean validateEmail(UserModel userModel) {
        Pattern pattern = Pattern.compile(EMAIL_VALIDATION_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userModel.getEmail());
       // return matcher.find();
        return true;
    }

    @Override
    public boolean validatePassword(UserModel userModel) {
        Pattern pattern = Pattern.compile(PASSWORD_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(userModel.getPassword());
        return matcher.find();
    }
}
