package com.epam.training.ticketservice.movie.repository;

import com.epam.training.ticketservice.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, String> {
}