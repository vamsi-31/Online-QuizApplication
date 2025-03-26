package com.onlinequiz.services.impl;
import com.onlinequiz.dao.UserDAO;
import com.onlinequiz.exception.InvalidRoleException;
import com.onlinequiz.exception.UserAlreadyExistsException;
import com.onlinequiz.models.User;
import com.onlinequiz.services.UserService;
import com.onlinequiz.exception.UserException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.onlinequiz.constants.Constants.*;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDAO userDAO;
    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        logger.info("UserServiceImpl initialized");
        this.userDAO = userDAO;
    }

    @Override
    public User createUser(String username, String password, String role) {
        logger.info("Creating user: {}", username);
        if (username == null || username.trim().isEmpty()) {
            throw new UserException(ERROR_EMPTY_USERNAME);
        }
        if (password == null || password.trim().isEmpty()) {
            throw new UserException(ERROR_EMPTY_PASSWORD);
        }
        if (role == null || (!role.equals("ADMIN") && !role.equals("USER"))) {
            throw new InvalidRoleException(ERROR_INVALID_ROLE);
        }

        if (userDAO.getUserByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException(ERROR_USER_EXISTS);
        }
        User user = new User(UUID.randomUUID().toString(), username, password, role);
        return userDAO.createUser(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new UserException(ERROR_EMPTY_USER_ID);
        }
        return userDAO.getUserById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new UserException(ERROR_EMPTY_USERNAME);
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
            throw new UserException(ERROR_EMPTY_USER);
        }
        return userDAO.updateUser(user);
    }

    @Override
    public boolean isDeleteUser(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new UserException(ERROR_EMPTY_USER_ID);
        }
        return userDAO.isDeleteUser(id);
    }
    @Override
    public User login(String username, String password) {
        Optional<User> userOpt = userDAO.authenticateUser(username, password);
        return userOpt.orElse(null);
    }
    @Override
    public User register(String username, String password, String role) {
        if (username == null || username.trim().isEmpty()) {
            throw new UserException(ERROR_EMPTY_USERNAME);
        }
        if (password == null || password.trim().isEmpty()) {
            throw new UserException(ERROR_EMPTY_PASSWORD);
        }
        if (role == null || (!role.equals("ADMIN") && !role.equals("USER"))) {
            throw new InvalidRoleException(ERROR_INVALID_ROLE);
        }

        if (userDAO.getUserByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException(ERROR_USER_EXISTS);
        }
        User user = new User(UUID.randomUUID().toString(), username, password, role);
        return userDAO.createUser(user);
    }

}