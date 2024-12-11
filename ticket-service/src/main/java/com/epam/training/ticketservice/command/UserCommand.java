package com.epam.training.ticketservice.command;

import com.epam.training.ticketservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class UserCommand {

    private final UserService userService;

    @Autowired
    public UserCommand(UserService userService) {
        this.userService = userService;
    }

    @ShellMethod(value = "Sign in as admin", key = "sign in privileged")
    public String signInPrivileged(@ShellOption String username, @ShellOption String password) {
        return userService.signIn(username, password);
    }

    @ShellMethod(value = "Sign out as admin", key = "sign out")
    public String signOut() {
        return userService.signOut();
    }

    @ShellMethod(value = "Describe the current account", key = "describe account")
    public String describeAccount() {
        return userService.describeAccount();
    }
}