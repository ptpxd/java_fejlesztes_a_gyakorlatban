package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    Optional<Screening> findByMovieTitleAndRoomNameAndStartTime(String movieTitle,
                                                                String roomName,
                                                                LocalDateTime startTime);
}