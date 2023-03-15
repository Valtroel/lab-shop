package com.example.labshop.repository;

import com.example.labshop.model.UserModel;

import java.util.List;

public interface UserRepository {

    boolean saveUser(UserModel userModel);
    boolean updateUser(UserModel userModel);
    boolean deleteUser(Long userId);
    UserModel findUserById(Long userId);
    UserModel findUserByEmail(String email);
    UserModel findUserByNickname(String nickname);

    List<UserModel> findAll();
}
