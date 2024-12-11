package com.epam.training.ticketservice.command;

import com.epam.training.ticketservice.service.UserService;
import com.epam.training.ticketservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class RoomCommand {

    private final UserService userService;
    private final RoomService roomService;

    @Autowired
    public RoomCommand(UserService userService, RoomService roomService) {
        this.userService = userService;
        this.roomService = roomService;
    }

    @ShellMethod(value = "Create a new room", key = "create room")
    public String createRoom(@ShellOption String name, @ShellOption int rows, @ShellOption int columns) {
        if (!userService.isLoggedIn()) {
            return "You must be signed in as an admin to use this command";
        }
        return roomService.createRoom(name, rows, columns);
    }

    @ShellMethod(value = "Update an existing room", key = "update room")
    public String updateRoom(@ShellOption String name, @ShellOption int rows, @ShellOption int columns) {
        if (!userService.isLoggedIn()) {
            return "You must be signed in as an admin to use this command";
        }
        return roomService.updateRoom(name, rows, columns);
    }

    @ShellMethod(value = "Delete an existing room", key = "delete room")
    public String deleteRoom(@ShellOption String name) {
        if (!userService.isLoggedIn()) {
            return "You must be signed in as an admin to use this command";
        }
        return roomService.deleteRoom(name);
    }

    @ShellMethod(value = "List all rooms", key = "list rooms")
    public String listRooms() {
        return roomService.listRooms();
    }
}