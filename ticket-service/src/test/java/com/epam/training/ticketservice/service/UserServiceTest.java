package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        User admin = new User("admin", "admin");
        when(userRepository.save(admin)).thenReturn(admin);
        userService.init();
    }

    @Test
    void testSignInSuccess() {
        String result = userService.signIn("admin", "admin");
        assertEquals("Signed in with privileged account 'admin'", result);
        verify(userRepository, times(2)).save(any(User.class));
    }

    @Test
    void testSignInFailure() {
        String result = userService.signIn("admin", "wrongpassword");
        assertEquals("Login failed due to incorrect credentials", result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testSignOutSuccess() {
        userService.signIn("admin", "admin");
        String result = userService.signOut();
        assertEquals("Signed out successfully", result);
        verify(userRepository, times(3)).save(any(User.class));
    }

    @Test
    void testSignOutFailure() {
        String result = userService.signOut();
        assertEquals("You are not signed in", result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDescribeAccountSignedIn() {
        userService.signIn("admin", "admin");
        String result = userService.describeAccount();
        assertEquals("Signed in with privileged account 'admin'", result);
    }

    @Test
    void testDescribeAccountNotSignedIn() {
        String result = userService.describeAccount();
        assertEquals("You are not signed in", result);
    }

    @Test
    void testIsLoggedIn() {
        userService.signIn("admin", "admin");
        boolean result = userService.isLoggedIn();
        assertEquals(true, result);
    }

    @Test
    void testIsNotLoggedIn() {
        boolean result = userService.isLoggedIn();
        assertEquals(false, result);
    }
}