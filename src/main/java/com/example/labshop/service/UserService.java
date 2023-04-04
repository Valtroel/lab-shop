package com.example.labshop.service;

import com.example.labshop.enumeration.AuthUserInfo;
import com.example.labshop.model.UserModel;
import com.example.labshop.repository.UserRepository;
import com.example.labshop.service.validator.UserParamsValidator;
import com.example.labshop.util.PasswordUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.labshop.enumeration.AuthUserInfo.*;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private static final Logger log = LogManager.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserParamsValidator userParamsValidator;

    public UserService(UserRepository userRepository, UserParamsValidator userParamsValidator) {
        this.userRepository = userRepository;
        this.userParamsValidator = userParamsValidator;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findUserByNickname(username);
        if (user == null){
            throw new UsernameNotFoundException("Пользователя с таким(" + username + ") именем не существует");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getAuthorities());
    }

    public AuthUserInfo createUser(UserModel userModel) {
        if (userModel == null) {
            log.info(USER_DATA_IS_EMPTY.getMessage());
            return USER_DATA_IS_EMPTY;
        } else if (!userParamsValidator.validateEmail(userModel)) {
            log.info(USER_EMAIL_IS_INVALID.getMessage());
            return USER_EMAIL_IS_INVALID;
        } else if (!userParamsValidator.validatePassword(userModel)) {
            log.info(USER_PASSWORD_IS_INVALID.getMessage());
            return USER_PASSWORD_IS_INVALID;
        } else if (userRepository.findUserByEmail(userModel.getEmail()) != null){
            log.info(USER_IS_EXIST.getMessage());
            return USER_IS_EXIST;
        }
        String hashedPassword = PasswordUtil.hashPassword(userModel.getPassword());
        userModel.setPassword(hashedPassword);
        userRepository.saveUser(userModel);
        log.info(SUCCESS.getMessage());
        return SUCCESS;
    }

    public AuthUserInfo verifyUserLoginData(UserModel userModel){
        if (userModel == null){
            log.info(USER_DATA_IS_EMPTY.getMessage());
            return USER_DATA_IS_EMPTY;
        }else if (!userParamsValidator.validateEmail(userModel)) {
            log.info(USER_EMAIL_IS_INVALID.getMessage());
            return USER_EMAIL_IS_INVALID;
        } else if (!userParamsValidator.validatePassword(userModel)) {
            log.info(USER_PASSWORD_IS_INVALID.getMessage());
            return USER_PASSWORD_IS_INVALID;
        } else if (!isUserExist(userModel.getEmail())){
            log.info(USER_EMAIL_IS_NOT_EXIST.getMessage());
            return USER_EMAIL_IS_NOT_EXIST;
        }

        UserModel storedUser = userRepository.findUserByEmail(userModel.getEmail());

        if (!PasswordUtil.checkPassword(userModel.getPassword(), storedUser.getPassword())){
            log.info(USER_PASSWORD_IS_INVALID);
            return USER_PASSWORD_IS_INVALID;
        }
        return SUCCESS;
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
