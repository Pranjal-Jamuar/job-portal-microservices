package com.jobportal.userservice.service;

import com.jobportal.userservice.entity.User;
import com.jobportal.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ TEST 1: Register user
    @Test
    void testRegisterUser_success() {

        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("123456");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("encodedPassword");
        when(userRepository.count()).thenReturn(1L);
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User saved = userService.registerUser(user);

        assertNotNull(saved);
        assertEquals("encodedPassword", saved.getPassword());
        verify(userRepository).save(any(User.class));
    }

    // ✅ TEST 2: Login success
    @Test
    void testLogin_success() {

        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "encodedPassword")).thenReturn(true);

        User result = userService.login("test@test.com", "123456");

        assertNotNull(result);
        assertEquals("test@test.com", result.getEmail());
    }

    // ❌ TEST 3: Login failure
    @Test
    void testLogin_invalidPassword() {

        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encodedPassword")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                userService.login("test@test.com", "wrong")
        );

        assertEquals("Invalid password", ex.getMessage());
    }
}