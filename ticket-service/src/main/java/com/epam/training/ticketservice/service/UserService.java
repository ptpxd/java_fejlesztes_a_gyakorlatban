package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserService {

    private final UserRepository userRepository;
    private User user;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        this.user = new User("admin", "admin");
        userRepository.save(user);
    }

    public String signIn(String username, String password) {
        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
            user.setLoggedIn(true);
            userRepository.save(user);
            return "Signed in with privileged account '" + username + "'";
        } else {
            return "Login failed due to incorrect credentials";
        }
    }

    public String signOut() {
        if (user.isLoggedIn()) {
            user.setLoggedIn(false);
            userRepository.save(user);
            return "Signed out successfully";
        } else {
            return "You are not signed in";
        }
    }

    public String describeAccount() {
        if (user.isLoggedIn()) {
            return "Signed in with privileged account '" + user.getUsername() + "'";
        } else {
            return "You are not signed in";
        }
    }

    public boolean isLoggedIn() {
        return user.isLoggedIn();
    }
}