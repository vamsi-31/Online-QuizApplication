package com.onlinequiz.services.impl;

import com.onlinequiz.dao.UserDAO;
import com.onlinequiz.models.User;
import com.onlinequiz.services.UserService;
import com.onlinequiz.exception.UserException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    public UserServiceImpl(UserDAO userDAO) {
        logger.info("UserServiceImpl initialized");
        this.userDAO = userDAO;
    }

    @Override
    public User createUser(String username, String password, String role) {
        logger.info("Creating user: {}", username);
        if (username == null || username.trim().isEmpty()) {
            throw new UserException("Username cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new UserException("Password cannot be empty");
        }
        if (role == null || (!role.equals("ADMIN") && !role.equals("USER"))) {
            throw new UserException("Invalid role. Must be either ADMIN or USER");
        }

        if (userDAO.getUserByUsername(username).isPresent()) {
            throw new UserException("Username already exists");
        }
        User user = new User(UUID.randomUUID().toString(), username, password, role);
        return userDAO.createUser(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new UserException("User ID cannot be empty");
        }
        return userDAO.getUserById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new UserException("Username cannot be empty");
        }
        return userDAO.getUserByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User updateUser(User user) {
        if (user == null) {
            throw new UserException("User cannot be null");
        }
        return userDAO.updateUser(user);
    }

    @Override
    public boolean deleteUser(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new UserException("User ID cannot be empty");
        }
        return userDAO.deleteUser(id);
    }

    @Override
    public Optional<User> authenticateUser(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new UserException("Username cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new UserException("Password cannot be empty");
        }
        return userDAO.authenticateUser(username, password);
    }
}