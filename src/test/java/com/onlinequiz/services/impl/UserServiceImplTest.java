package com.onlinequiz.services.impl;

import com.onlinequiz.dao.UserDAO;
import com.onlinequiz.exception.InvalidRoleException;
import com.onlinequiz.exception.UserAlreadyExistsException;
import com.onlinequiz.exception.UserException;
import com.onlinequiz.models.User;
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

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userDAO);
    }

    @Test
    void createUser_Success() {
        // Arrange
        String username = "testuser";
        String password = "password123";
        String role = "USER";
        when(userDAO.getUserByUsername(username)).thenReturn(Optional.empty());
        when(userDAO.createUser(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        User result = userService.createUser(username, password, role);

        // Assert
        assertNotNull(result.getId());
        assertEquals(username, result.getUsername());
        assertEquals(password, result.getPassword());
        assertEquals(role, result.getRole());
        verify(userDAO, times(1)).createUser(any(User.class));
    }

    @Test
    void createUser_EmptyUsername_ThrowsException() {
        // Act & Assert
        assertThrows(UserException.class, () -> userService.createUser("", "password", "USER"));
    }

    @Test
    void createUser_EmptyPassword_ThrowsException() {
        // Act & Assert
        assertThrows(UserException.class, () -> userService.createUser("username", "", "USER"));
    }

    @Test
    void createUser_InvalidRole_ThrowsException() {
        // Act & Assert
        assertThrows(InvalidRoleException.class, () -> userService.createUser("username", "password", "INVALID_ROLE"));
    }

    @Test
    void createUser_ExistingUsername_ThrowsException() {
        // Arrange
        String username = "existinguser";
        when(userDAO.getUserByUsername(username)).thenReturn(Optional.of(new User("1", username, "password", "USER")));

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(username, "password", "USER"));
    }

    @Test
    void getUserById_Success() {
        // Arrange
        String id = "user1";
        User expectedUser = new User(id, "testuser", "password", "USER");
        when(userDAO.getUserById(id)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<User> result = userService.getUserById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
        verify(userDAO, times(1)).getUserById(id);
    }

    @Test
    void getUserById_EmptyId_ThrowsException() {
        // Act & Assert
        assertThrows(UserException.class, () -> userService.getUserById(""));
    }

    @Test
    void getUserByUsername_Success() {
        // Arrange
        String username = "testuser";
        User expectedUser = new User("1", username, "password", "USER");
        when(userDAO.getUserByUsername(username)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<User> result = userService.getUserByUsername(username);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
        verify(userDAO, times(1)).getUserByUsername(username);
    }

    @Test
    void getUserByUsername_EmptyUsername_ThrowsException() {
        // Act & Assert
        assertThrows(UserException.class, () -> userService.getUserByUsername(""));
    }

    @Test
    void getAllUsers_Success() {
        // Arrange
        List<User> expectedUsers = Arrays.asList(
                new User("1", "user1", "password1", "USER"),
                new User("2", "user2", "password2", "ADMIN")
        );
        when(userDAO.getAllUsers()).thenReturn(expectedUsers);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertEquals(expectedUsers, result);
        verify(userDAO, times(1)).getAllUsers();
    }

    @Test
    void updateUser_Success() {
        // Arrange
        User user = new User("1", "updateduser", "newpassword", "ADMIN");
        when(userDAO.updateUser(user)).thenReturn(user);

        // Act
        User result = userService.updateUser(user);

        // Assert
        assertEquals(user, result);
        verify(userDAO, times(1)).updateUser(user);
    }

    @Test
    void updateUser_NullUser_ThrowsException() {
        // Act & Assert
        assertThrows(UserException.class, () -> userService.updateUser(null));
    }

    @Test
    void deleteUser_Success() {
        // Arrange
        String id = "user1";
        when(userDAO.isDeleteUser(id)).thenReturn(true);

        // Act
        boolean result = userService.isDeleteUser(id);

        // Assert
        assertTrue(result);
        verify(userDAO, times(1)).isDeleteUser(id);
    }

    @Test
    void deleteUser_EmptyId_ThrowsException() {
        // Act & Assert
        assertThrows(UserException.class, () -> userService.isDeleteUser(""));
    }

    @Test
    void login_Success() {
        // Arrange
        String username = "testuser";
        String password = "password";
        User expectedUser = new User("1", username, password, "USER");
        when(userDAO.authenticateUser(username, password)).thenReturn(Optional.of(expectedUser));

        // Act
        User result = userService.login(username, password);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUser, result);
        verify(userDAO, times(1)).authenticateUser(username, password);
    }

    @Test
    void login_Failed() {
        // Arrange
        String username = "testuser";
        String password = "wrongpassword";
        when(userDAO.authenticateUser(username, password)).thenReturn(Optional.empty());

        // Act
        User result = userService.login(username, password);

        // Assert
        assertNull(result);
        verify(userDAO, times(1)).authenticateUser(username, password);
    }

    @Test
    void register_Success() {
        // Arrange
        String username = "newuser";
        String password = "password123";
        String role = "USER";
        when(userDAO.getUserByUsername(username)).thenReturn(Optional.empty());
        when(userDAO.createUser(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        User result = userService.register(username, password, role);

        // Assert
        assertNotNull(result.getId());
        assertEquals(username, result.getUsername());
        assertEquals(password, result.getPassword());
        assertEquals(role, result.getRole());
        verify(userDAO, times(1)).createUser(any(User.class));
    }

    @Test
    void register_ExistingUsername_ThrowsException() {
        // Arrange
        String username = "existinguser";
        when(userDAO.getUserByUsername(username)).thenReturn(Optional.of(new User("1", username, "password", "USER")));

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.register(username, "password", "USER"));
    }
}