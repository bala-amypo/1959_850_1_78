package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
    User createUser(String email, String password, String role);
    User findByEmail(String email);
}