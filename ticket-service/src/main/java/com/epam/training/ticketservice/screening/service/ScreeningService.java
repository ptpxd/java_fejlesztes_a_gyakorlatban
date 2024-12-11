package com.epam.training.ticketservice.screening.service;

import com.epam.training.ticketservice.movie.service.MovieService;
import com.epam.training.ticketservice.screening.model.Screening;
import com.epam.training.ticketservice.screening.model.ScreeningId;
import com.epam.training.ticketservice.screening.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieService movieService;

    @Autowired
    public ScreeningService(ScreeningRepository screeningRepository, MovieService movieService) {
        this.screeningRepository = screeningRepository;
        this.movieService = movieService;
    }

    public String createScreening(String movieTitle, String roomName, LocalDateTime startTime) {
        int duration = movieService.getMovieDuration(movieTitle);
        List<Screening> screenings = screeningRepository.findAll();
        for (Screening screening : screenings) {
            if (screening.getRoomName().equals(roomName)) {
                LocalDateTime endTime = startTime.plusMinutes(duration);
                LocalDateTime existingEndTime = screening.getStartTime().plusMinutes(screening.getDuration());

                if (startTime.isBefore(existingEndTime) && endTime.isAfter(screening.getStartTime())) {
                    return "There is an overlapping screening";
                }

                if (startTime.isBefore(existingEndTime.plusMinutes(10)) && endTime.isAfter(screening.getStartTime())) {
                    return "This would start in the break period after another screening in this room";
                }
            }
        }
        screeningRepository.save(new Screening(movieTitle, roomName, startTime, duration));
        return "Screening created successfully";
    }

    public String deleteScreening(String movieTitle, String roomName, LocalDateTime startTime) {
        ScreeningId screeningId = new ScreeningId(movieTitle, roomName, startTime);
        if (screeningRepository.existsById(screeningId)) {
            screeningRepository.deleteById(screeningId);
            return "Screening deleted successfully";
        }
        return "Screening not found";
    }

    public String listScreenings() {
        List<Screening> screenings = screeningRepository.findAll();
        if (screenings.isEmpty()) {
            return "There are no screenings";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return screenings.stream()
                .map(screening -> String.format("%s (%s, %d minutes), screened in room %s, at %s",
                        screening.getMovieTitle(),
                        movieService.getMovieGenre(screening.getMovieTitle()),
                        screening.getDuration(),
                        screening.getRoomName(),
                        screening.getStartTime().format(formatter)))
                .collect(Collectors.joining("\n"));
    }
}