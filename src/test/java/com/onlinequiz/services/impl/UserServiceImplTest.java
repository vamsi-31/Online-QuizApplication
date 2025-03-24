package com.onlinequiz.services.impl;

import com.onlinequiz.dao.UserDAO;
import com.onlinequiz.models.User;
import com.onlinequiz.services.UserService;
import com.onlinequiz.exception.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserDAO userDAO;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userDAO);
    }

    @Test
    void testCreateUser() {
        String username = "testuser";
        String password = "password";
        String role = "USER";
        User expectedUser = new User("1", username, password, role);

        when(userDAO.getUserByUsername(username)).thenReturn(Optional.empty());
        when(userDAO.createUser(any(User.class))).thenReturn(expectedUser);

        User createdUser = userService.createUser(username, password, role);

        assertNotNull(createdUser);
        assertEquals(username, createdUser.getUsername());
        assertEquals(role, createdUser.getRole());
        verify(userDAO, times(1)).createUser(any(User.class));
    }

    @Test
    void testCreateUserWithExistingUsername() {
        String username = "existinguser";
        String password = "password";
        String role = "USER";

        when(userDAO.getUserByUsername(username)).thenReturn(Optional.of(new User("1", username, password, role)));

        assertThrows(UserException.class, () -> userService.createUser(username, password, role));
    }

    @Test
    void testGetUserById() {
        String userId = "1";
        User expectedUser = new User(userId, "testuser", "password", "USER");

        when(userDAO.getUserById(userId)).thenReturn(Optional.of(expectedUser));

        Optional<User> retrievedUser = userService.getUserById(userId);

        assertTrue(retrievedUser.isPresent());
        assertEquals(userId, retrievedUser.get().getId());
        verify(userDAO, times(1)).getUserById(userId);
    }

    @Test
    void testGetUserByUsername() {
        String username = "testuser";
        User expectedUser = new User("1", username, "password", "USER");

        when(userDAO.getUserByUsername(username)).thenReturn(Optional.of(expectedUser));

        Optional<User> retrievedUser = userService.getUserByUsername(username);

        assertTrue(retrievedUser.isPresent());
        assertEquals(username, retrievedUser.get().getUsername());
        verify(userDAO, times(1)).getUserByUsername(username);
    }

    @Test
    void testGetAllUsers() {
        List<User> expectedUsers = Arrays.asList(
                new User("1", "user1", "password", "USER"),
                new User("2", "user2", "password", "ADMIN")
        );

        when(userDAO.getAllUsers()).thenReturn(expectedUsers);

        List<User> retrievedUsers = userService.getAllUsers();

        assertEquals(2, retrievedUsers.size());
        verify(userDAO, times(1)).getAllUsers();
    }

    @Test
    void testUpdateUser() {
        User userToUpdate = new User("1", "updateduser", "newpassword", "ADMIN");

        when(userDAO.updateUser(userToUpdate)).thenReturn(userToUpdate);

        User updatedUser = userService.updateUser(userToUpdate);

        assertEquals("updateduser", updatedUser.getUsername());
        assertEquals("ADMIN", updatedUser.getRole());
        verify(userDAO, times(1)).updateUser(userToUpdate);
    }

    @Test
    void testDeleteUser() {
        String userId = "1";

        when(userDAO.deleteUser(userId)).thenReturn(true);

        boolean deleted = userService.deleteUser(userId);

        assertTrue(deleted);
        verify(userDAO, times(1)).deleteUser(userId);
    }

    @Test
    void testAuthenticateUser() {
        String username = "testuser";
        String password = "password";
        User expectedUser = new User("1", username, password, "USER");

        when(userDAO.authenticateUser(username, password)).thenReturn(Optional.of(expectedUser));

        Optional<User> authenticatedUser = userService.authenticateUser(username, password);

        assertTrue(authenticatedUser.isPresent());
        assertEquals(username, authenticatedUser.get().getUsername());
        verify(userDAO, times(1)).authenticateUser(username, password);
    }
}