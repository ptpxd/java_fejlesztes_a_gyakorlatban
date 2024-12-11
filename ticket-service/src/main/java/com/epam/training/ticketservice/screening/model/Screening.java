package com.epam.training.ticketservice.screening.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Screening {
    @EmbeddedId
    private ScreeningId id;
    private int duration;

    public Screening(String movieTitle, String roomName, LocalDateTime startTime, int duration) {
        this.id = new ScreeningId(movieTitle, roomName, startTime);
        this.duration = duration;
    }

    public String getMovieTitle() {
        return id.getMovieTitle();
    }

    public String getRoomName() {
        return id.getRoomName();
    }

    public LocalDateTime getStartTime() {
        return id.getStartTime();
    }
}