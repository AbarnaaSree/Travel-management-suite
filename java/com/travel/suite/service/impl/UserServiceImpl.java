package com.travel.suite.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.suite.model.User;
import com.travel.suite.repository.UserRepository;
import com.travel.suite.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;  // Your repository for DB interaction

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);  // Find user by username
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);  // Check if the username exists
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);  // Save or update user in the database
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();  // Retrieve all users from the database
    }
    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);  // Find user by ID, return null if not found
    }
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);  // Return user or null if not found
    }
    @Override
    public boolean deleteUserByIdIfRoleUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Check if the user has the role 'user'
            if ("user".equalsIgnoreCase(user.getRole())) {
                userRepository.deleteById(id);
                return true;
            }
        }
        
        // Return false if user not found or doesn't have role 'user'
        return false;
    }
     
    
    
}
