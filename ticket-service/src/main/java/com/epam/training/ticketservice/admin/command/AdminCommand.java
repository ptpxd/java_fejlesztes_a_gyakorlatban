package com.epam.training.ticketservice.admin.command;

import com.epam.training.ticketservice.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class AdminCommand {

    private final AdminService adminService;

    @Autowired
    public AdminCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @ShellMethod(value = "Sign in as admin", key = "sign in privileged")
    public String signInPrivileged(@ShellOption String username, @ShellOption String password) {
        return adminService.signIn(username, password);
    }

    @ShellMethod(value = "Sign out as admin", key = "sign out")
    public String signOut() {
        return adminService.signOut();
    }

    @ShellMethod(value = "Describe the current account", key = "describe account")
    public String describeAccount() {
        return adminService.describeAccount();
    }
}