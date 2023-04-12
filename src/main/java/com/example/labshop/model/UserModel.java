package com.example.labshop.model;

import com.example.labshop.enumeration.UserRole;

import java.util.Collection;
import java.util.List;

public class UserModel{

    private Long id;
    private String username;
    private String email;
    private String password;
    private UserRole role;


    public UserModel(Long id,
                     String username,
                     String email,
                     String password,
                     UserRole role)
    {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserModel() {
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }
}
