package com.onlinequiz.dao;

import com.onlinequiz.models.User;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for User-related operations.
 */
public interface UserDAO {
    /**
     * Create a new user.
     *
     * @param user The user to be created.
     * @return The created user with generated ID.
     */
    User createUser(User user);

    /**
     * Retrieve a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return An Optional containing the user if found, or empty if not found.
     */
    Optional<User> getUserById(String id);

    /**
     * Retrieve a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return An Optional containing the user if found, or empty if not found.
     */
    Optional<User> getUserByUsername(String username);

    /**
     * Retrieve all users.
     *
     * @return A list of all users.
     */
    List<User> getAllUsers();

    /**
     * Update an existing user.
     *
     * @param user The user with updated information.
     * @return The updated user.
     */
    User updateUser(User user);

    /**
     * Delete a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @return true if the user was successfully deleted, false otherwise.
     */
    boolean deleteUser(String id);

    /**
     * Authenticate a user.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return An Optional containing the authenticated user if successful, or empty if authentication fails.
     */
    Optional<User> authenticateUser(String username, String password);
}