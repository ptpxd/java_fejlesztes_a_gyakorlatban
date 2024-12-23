package com.epam.training.ticketservice.command;

import com.epam.training.ticketservice.service.UserService;
import com.epam.training.ticketservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class MovieCommand {

    private final UserService userService;
    private final MovieService movieService;

    @Autowired
    public MovieCommand(UserService userService, MovieService movieService) {
        this.userService = userService;
        this.movieService = movieService;
    }

    @ShellMethod(value = "Create a new movie", key = "create movie")
    public String createMovie(@ShellOption String title, @ShellOption String genre, @ShellOption int duration) {
        if (!userService.isLoggedIn()) {
            return "You must be signed in as an admin to use this command";
        }
        return movieService.createMovie(title, genre, duration);
    }

    @ShellMethod(value = "Update an existing movie", key = "update movie")
    public String updateMovie(@ShellOption String title, @ShellOption String genre, @ShellOption int duration) {
        if (!userService.isLoggedIn()) {
            return "You must be signed in as an admin to use this command";
        }
        return movieService.updateMovie(title, genre, duration);
    }

    @ShellMethod(value = "Delete an existing movie", key = "delete movie")
    public String deleteMovie(@ShellOption String title) {
        if (!userService.isLoggedIn()) {
            return "You must be signed in as an admin to use this command";
        }
        return movieService.deleteMovie(title);
    }

    @ShellMethod(value = "List all movies", key = "list movies")
    public String listMovies() {
        return movieService.listMovies();
    }
}