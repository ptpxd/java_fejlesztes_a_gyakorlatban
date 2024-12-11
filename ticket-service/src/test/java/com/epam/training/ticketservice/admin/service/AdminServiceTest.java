package com.epam.training.ticketservice.admin.service;

import com.epam.training.ticketservice.admin.model.Admin;
import com.epam.training.ticketservice.admin.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Admin admin = new Admin("admin", "admin");
        when(adminRepository.save(admin)).thenReturn(admin);
        adminService.init();
    }

    @Test
    void testSignInSuccess() {
        String result = adminService.signIn("admin", "admin");
        assertEquals("Signed in with privileged account 'admin'", result);
        verify(adminRepository, times(2)).save(any(Admin.class));
    }

    @Test
    void testSignInFailure() {
        String result = adminService.signIn("admin", "wrongpassword");
        assertEquals("Login failed due to incorrect credentials", result);
        verify(adminRepository, times(1)).save(any(Admin.class));
    }

    @Test
    void testSignOutSuccess() {
        adminService.signIn("admin", "admin");
        String result = adminService.signOut();
        assertEquals("Signed out successfully", result);
        verify(adminRepository, times(3)).save(any(Admin.class));
    }

    @Test
    void testSignOutFailure() {
        String result = adminService.signOut();
        assertEquals("You are not signed in", result);
        verify(adminRepository, times(1)).save(any(Admin.class));
    }

    @Test
    void testDescribeAccountSignedIn() {
        adminService.signIn("admin", "admin");
        String result = adminService.describeAccount();
        assertEquals("Signed in with privileged account 'admin'", result);
    }

    @Test
    void testDescribeAccountNotSignedIn() {
        String result = adminService.describeAccount();
        assertEquals("You are not signed in", result);
    }

    @Test
    void testIsLoggedIn() {
        adminService.signIn("admin", "admin");
        boolean result = adminService.isLoggedIn();
        assertEquals(true, result);
    }

    @Test
    void testIsNotLoggedIn() {
        boolean result = adminService.isLoggedIn();
        assertEquals(false, result);
    }
}