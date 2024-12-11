package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ScreeningServiceTest {

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private ScreeningService screeningService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateScreening() {
        String movieTitle = "Test Movie";
        String roomName = "Test Room";
        LocalDateTime startTime = LocalDateTime.of(2023, 10, 10, 10, 0);
        int duration = 120;

        when(movieService.getMovieDuration(movieTitle)).thenReturn(duration);
        when(screeningRepository.findAll()).thenReturn(Collections.emptyList());

        String result = screeningService.createScreening(movieTitle, roomName, startTime);

        assertEquals("Screening created successfully", result);
        verify(screeningRepository, times(1)).save(any(Screening.class));
    }

    @Test
    void testDeleteScreening() {
        String movieTitle = "Test Movie";
        String roomName = "Test Room";
        LocalDateTime startTime = LocalDateTime.of(2023, 10, 10, 10, 0);
        Screening screening = new Screening(movieTitle, roomName, startTime, 120);

        when(screeningRepository.findByMovieTitleAndRoomNameAndStartTime(movieTitle, roomName, startTime))
                .thenReturn(Optional.of(screening));

        String result = screeningService.deleteScreening(movieTitle, roomName, startTime);

        assertEquals("Screening deleted successfully", result);
        verify(screeningRepository, times(1)).delete(screening);
    }

    @Test
    void testListScreenings() {
        Screening screening = new Screening("Test Movie", "Test Room", LocalDateTime.of(2023, 10, 10, 10, 0), 120);

        when(screeningRepository.findAll()).thenReturn(Collections.singletonList(screening));
        when(movieService.getMovieGenre("Test Movie")).thenReturn("Drama");

        String result = screeningService.listScreenings();

        assertEquals("Test Movie (Drama, 120 minutes), screened in room Test Room, at 2023-10-10 10:00", result);
    }
}