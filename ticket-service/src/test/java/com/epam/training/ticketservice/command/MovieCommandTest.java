package com.epam.training.ticketservice.command;

import com.epam.training.ticketservice.service.UserService;
import com.epam.training.ticketservice.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MovieCommandTest {

    @Mock
    private UserService userService;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieCommand movieCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMovieWhenNotLoggedIn() {
        when(userService.isLoggedIn()).thenReturn(false);

        String result = movieCommand.createMovie("Title", "Genre", 120);

        assertEquals("You must be signed in as an admin to use this command", result);
        verify(movieService, never()).createMovie(anyString(), anyString(), anyInt());
    }

    @Test
    void testCreateMovieWhenLoggedIn() {
        when(userService.isLoggedIn()).thenReturn(true);
        when(movieService.createMovie("Title", "Genre", 120)).thenReturn("Movie created successfully");

        String result = movieCommand.createMovie("Title", "Genre", 120);

        assertEquals("Movie created successfully", result);
        verify(movieService, times(1)).createMovie("Title", "Genre", 120);
    }

    @Test
    void testUpdateMovieWhenNotLoggedIn() {
        when(userService.isLoggedIn()).thenReturn(false);

        String result = movieCommand.updateMovie("Title", "Genre", 120);

        assertEquals("You must be signed in as an admin to use this command", result);
        verify(movieService, never()).updateMovie(anyString(), anyString(), anyInt());
    }

    @Test
    void testUpdateMovieWhenLoggedIn() {
        when(userService.isLoggedIn()).thenReturn(true);
        when(movieService.updateMovie("Title", "Genre", 120)).thenReturn("Movie updated successfully");

        String result = movieCommand.updateMovie("Title", "Genre", 120);

        assertEquals("Movie updated successfully", result);
        verify(movieService, times(1)).updateMovie("Title", "Genre", 120);
    }

    @Test
    void testDeleteMovieWhenNotLoggedIn() {
        when(userService.isLoggedIn()).thenReturn(false);

        String result = movieCommand.deleteMovie("Title");

        assertEquals("You must be signed in as an admin to use this command", result);
        verify(movieService, never()).deleteMovie(anyString());
    }

    @Test
    void testDeleteMovieWhenLoggedIn() {
        when(userService.isLoggedIn()).thenReturn(true);
        when(movieService.deleteMovie("Title")).thenReturn("Movie deleted successfully");

        String result = movieCommand.deleteMovie("Title");

        assertEquals("Movie deleted successfully", result);
        verify(movieService, times(1)).deleteMovie("Title");
    }

    @Test
    void testListMovies() {
        when(movieService.listMovies()).thenReturn("List of movies");

        String result = movieCommand.listMovies();

        assertEquals("List of movies", result);
        verify(movieService, times(1)).listMovies();
    }
}