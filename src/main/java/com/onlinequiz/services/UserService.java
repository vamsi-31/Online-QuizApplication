package com.onlinequiz.services;

import com.onlinequiz.models.User;
import java.util.List;
import java.util.Optional;
public interface UserService {
    User createUser(String username, String password, String role);
    Optional<User> getUserById(String id);
    Optional<User> getUserByUsername(String username);
    List<User> getAllUsers();
    User updateUser(User user);
    boolean deleteUser(String id); // Changed from isDeleteUser
    User login(String username, String password);
    User register(String username, String password, String role);
}