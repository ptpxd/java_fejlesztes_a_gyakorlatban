package com.epam.training.ticketservice.admin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Admin {
    @Id
    private String username;
    private String password;
    private boolean loggedIn;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
        this.loggedIn = false;
    }
}