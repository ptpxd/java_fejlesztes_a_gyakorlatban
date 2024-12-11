package com.epam.training.ticketservice.movie.service;

import com.epam.training.ticketservice.movie.model.Movie;
import com.epam.training.ticketservice.movie.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMovie() {
        Movie movie = new Movie("Test Movie", "Drama", 120);

        when(movieRepository.existsById("Test Movie")).thenReturn(false);
        when(movieRepository.save(movie)).thenReturn(movie);

        String result = movieService.createMovie("Test Movie", "Drama", 120);

        assertEquals("Movie created successfully", result);
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    void testDeleteMovie() {
        String title = "Test Movie";

        when(movieRepository.existsById(title)).thenReturn(true);
        doNothing().when(movieRepository).deleteById(title);

        movieService.deleteMovie(title);

        verify(movieRepository, times(1)).deleteById(title);
    }

    @Test
    void testListMovies() {
        Movie movie = new Movie("Test Movie", "Drama", 120);

        when(movieRepository.findAll()).thenReturn(Collections.singletonList(movie));

        String result = movieService.listMovies();

        assertEquals("Test Movie (Drama, 120 minutes)", result);
    }
}