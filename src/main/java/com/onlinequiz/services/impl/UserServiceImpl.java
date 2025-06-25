package com.onlinequiz.services.impl;

import com.onlinequiz.repositories.UserRepository;
import com.onlinequiz.exception.InvalidRoleException;
import com.onlinequiz.exception.UserAlreadyExistsException;
import com.onlinequiz.models.User;
import com.onlinequiz.services.UserService;
import com.onlinequiz.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.onlinequiz.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(String username, String password, String role) {
        validateUserInput(username, password, role);

        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = User.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();

        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(username)
                .filter(u -> !u.trim().isEmpty())
                .flatMap(userRepository::findByUsername)
                .or(() -> {
                    throw new UserException(ERROR_EMPTY_USERNAME);
                });
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        return Optional.ofNullable(user)
                .map(userRepository::save)
                .orElseThrow(() -> new UserException(ERROR_INVALID_ROLE));
    }

    @Override
    @Transactional
    public boolean deleteUser(String id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public User login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .orElse(null);
    }

    @Override
    @Transactional
    public User register(String username, String password, String role) {
        return createUser(username, password, role);
    }

    private void validateUserInput(String username, String password, String role) {
        if (username == null || username.trim().isEmpty()) {
            throw new UserException(ERROR_EMPTY_USERNAME);
        }
        if (password == null || password.trim().isEmpty()) {
            throw new UserException(ERROR_EMPTY_PASSWORD);
        }
        if (role == null || (!role.equals(ROLE_ADMIN) && !role.equals("USER"))) {
            throw new InvalidRoleException(ERROR_INVALID_ROLE);
        }
    }
}