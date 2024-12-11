package com.epam.training.ticketservice.screening.repository;

import com.epam.training.ticketservice.screening.model.Screening;
import com.epam.training.ticketservice.screening.model.ScreeningId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, ScreeningId> {
}