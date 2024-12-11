package com.epam.training.ticketservice.movie.service;

import com.epam.training.ticketservice.movie.model.Movie;
import com.epam.training.ticketservice.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void addMovie(String title, String genre, int duration) {
        Movie movie = new Movie(title, genre, duration);
        movieRepository.save(movie);
    }

    public String createMovie(String title, String genre, int duration) {
        if (movieRepository.existsById(title)) {
            return "Movie already exists";
        }
        movieRepository.save(new Movie(title, genre, duration));
        return "Movie created successfully";
    }

    public String updateMovie(String title, String genre, int duration) {
        Optional<Movie> movieOptional = movieRepository.findById(title);
        if (!movieOptional.isPresent()) {
            return "Movie not found";
        }
        Movie movie = movieOptional.get();
        movie.setGenre(genre);
        movie.setDuration(duration);
        movieRepository.save(movie);
        return "Movie updated successfully";
    }

    public String deleteMovie(String title) {
        if (movieRepository.existsById(title)) {
            movieRepository.deleteById(title);
            return "Movie deleted successfully";
        } else {
            return "Movie not found";
        }
    }

    public String listMovies() {
        List<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        }
        return movies.stream()
                .map(movie -> String.format("%s (%s, %d minutes)", movie.getTitle(), movie.getGenre(), movie.getDuration()))
                .collect(Collectors.joining("\n"));
    }

    public int getMovieDuration(String title) {
        return movieRepository.findById(title)
                .map(Movie::getDuration)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));
    }

    public String getMovieGenre(String movieTitle) {
        return movieRepository.findById(movieTitle)
                .map(Movie::getGenre)
                .orElse("Unknown genre");
    }
}