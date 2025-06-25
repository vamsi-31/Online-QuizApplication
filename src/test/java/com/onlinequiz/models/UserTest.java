//package com.onlinequiz.models;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserTest {
//
//    @Test
//    void testUserConstructorAndGetters() {
//        String id = "U1";
//        String username = "john_doe";
//        String password = "securepass123";
//        String role = "USER";
//
//        User user = new User(id, username, password, role);
//
//        assertEquals(id, user.getId());
//        assertEquals(username, user.getUsername());
//        assertEquals(password, user.getPassword());
//        assertEquals(role, user.getRole());
//    }
//
//    @Test
//    void testUserSetters() {
//        User user = new User(null, null, null, null);
//
//        String id = "U2";
//        String username = "jane_smith";
//        String password = "strongpass456";
//        String role = "ADMIN";
//
//        user.setId(id);
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setRole(role);
//
//        assertEquals(id, user.getId());
//        assertEquals(username, user.getUsername());
//        assertEquals(password, user.getPassword());
//        assertEquals(role, user.getRole());
//    }
//}