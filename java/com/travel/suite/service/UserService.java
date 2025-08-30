package com.travel.suite.service;

import java.util.List;

import com.travel.suite.model.User;

public interface UserService {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    void saveUser(User user);
    List<User> getAllUsers();   
    User findById(Long id); 
    User findUserById(Long id);  
    
        List<User> findAllUsers();
    boolean deleteUserByIdIfRoleUser(Long id);
}
