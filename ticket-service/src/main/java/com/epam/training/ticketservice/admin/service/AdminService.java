package com.epam.training.ticketservice.admin.service;

import com.epam.training.ticketservice.admin.model.Admin;
import com.epam.training.ticketservice.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private Admin admin;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @PostConstruct
    public void init() {
        this.admin = new Admin("admin", "admin");
        adminRepository.save(admin);
    }

    public String signIn(String username, String password) {
        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            admin.setLoggedIn(true);
            adminRepository.save(admin);
            return "Signed in with privileged account '" + username + "'";
        } else {
            return "Login failed due to incorrect credentials";
        }
    }

    public String signOut() {
        if (admin.isLoggedIn()) {
            admin.setLoggedIn(false);
            adminRepository.save(admin);
            return "Signed out successfully";
        } else {
            return "You are not signed in";
        }
    }

    public String describeAccount() {
        if (admin.isLoggedIn()) {
            return "Signed in with privileged account '" + admin.getUsername() + "'";
        } else {
            return "You are not signed in";
        }
    }

    public boolean isLoggedIn() {
        return admin.isLoggedIn();
    }
}