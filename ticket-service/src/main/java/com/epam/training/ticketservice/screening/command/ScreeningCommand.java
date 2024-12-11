package com.epam.training.ticketservice.screening.command;

import com.epam.training.ticketservice.admin.service.AdminService;
import com.epam.training.ticketservice.screening.service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ShellComponent
public class ScreeningCommand {

    private final AdminService adminService;
    private final ScreeningService screeningService;

    @Autowired
    public ScreeningCommand(AdminService adminService, ScreeningService screeningService) {
        this.adminService = adminService;
        this.screeningService = screeningService;
    }

    @ShellMethod(value = "Create a new screening", key = "create screening")
    public String createScreening(@ShellOption String movieTitle,
                                  @ShellOption String roomName,
                                  @ShellOption String startTime) {
        if (!adminService.isLoggedIn()) {
            return "You must be signed in as an admin to use this command";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(startTime, formatter);
        return screeningService.createScreening(movieTitle, roomName, dateTime);
    }

    @ShellMethod(value = "Delete an existing screening", key = "delete screening")
    public String deleteScreening(@ShellOption String movieTitle,
                                  @ShellOption String roomName,
                                  @ShellOption String startTime) {
        if (!adminService.isLoggedIn()) {
            return "You must be signed in as an admin to use this command";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(startTime, formatter);
        return screeningService.deleteScreening(movieTitle, roomName, dateTime);
    }

    @ShellMethod(value = "List all screenings", key = "list screenings")
    public String listScreenings() {
        return screeningService.listScreenings();
    }
}