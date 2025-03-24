package com.onlinequiz.dao.impl;

import com.onlinequiz.dao.UserDAO;
import com.onlinequiz.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOImplTest {

    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAOImpl();
    }

    @Test
    void testCreateUser() {
        User user = new User("1", "testuser", "password", "USER");
        User createdUser = userDAO.createUser(user);
        assertNotNull(createdUser);
        assertEquals("1", createdUser.getId());
    }

    @Test
    void testGetUserById() {
        User user = new User("1", "testuser", "password", "USER");
        userDAO.createUser(user);
        Optional<User> retrievedUser = userDAO.getUserById("1");
        assertTrue(retrievedUser.isPresent());
        assertEquals("testuser", retrievedUser.get().getUsername());
    }

    @Test
    void testGetUserByUsername() {
        User user = new User("1", "testuser", "password", "USER");
        userDAO.createUser(user);
        Optional<User> retrievedUser = userDAO.getUserByUsername("testuser");
        assertTrue(retrievedUser.isPresent());
        assertEquals("1", retrievedUser.get().getId());
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User("1", "testuser1", "password", "USER");
        User user2 = new User("2", "testuser2", "password", "ADMIN");
        userDAO.createUser(user1);
        userDAO.createUser(user2);
        List<User> users = userDAO.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testUpdateUser() {
        User user = new User("1", "testuser", "password", "USER");
        userDAO.createUser(user);
        user.setUsername("updateduser");
        User updatedUser = userDAO.updateUser(user);
        assertEquals("updateduser", updatedUser.getUsername());
    }

    @Test
    void testDeleteUser() {
        User user = new User("1", "testuser", "password", "USER");
        userDAO.createUser(user);
        boolean deleted = userDAO.deleteUser("1");
        assertTrue(deleted);
        Optional<User> deletedUser = userDAO.getUserById("1");
        assertFalse(deletedUser.isPresent());
    }

    @Test
    void testAuthenticateUser() {
        User user = new User("1", "testuser", "password", "USER");
        userDAO.createUser(user);
        Optional<User> authenticatedUser = userDAO.authenticateUser("testuser", "password");
        assertTrue(authenticatedUser.isPresent());
        assertEquals("1", authenticatedUser.get().getId());
    }
}