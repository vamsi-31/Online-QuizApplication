package com.onlinequiz.dao.impl;

import com.onlinequiz.dao.UserDAO;
import com.onlinequiz.models.User;
import java.util.*;

public class UserDAOImpl implements UserDAO {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public User createUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User updateUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public boolean deleteUser(String id) {
        return users.remove(id) != null;
    }

    @Override
    public Optional<User> authenticateUser(String username, String password) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst();
    }
}