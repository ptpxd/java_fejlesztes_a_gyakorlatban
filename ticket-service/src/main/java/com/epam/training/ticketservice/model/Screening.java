package com.epam.training.ticketservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Screening {
    @Id
    @GeneratedValue
    private Long id;
    private String movieTitle;
    private String roomName;
    private LocalDateTime startTime;
    private int duration;

    public Screening(String movieTitle, String roomName, LocalDateTime startTime, int duration) {
        this.movieTitle = movieTitle;
        this.roomName = roomName;
        this.startTime = startTime;
        this.duration = duration;
    }
}