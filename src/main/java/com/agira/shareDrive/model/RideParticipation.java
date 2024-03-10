package com.agira.shareDrive.model;

import jakarta.persistence.*;

@Entity
public class RideParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    private Ride ride;
    private boolean is_driver;
    private String status;
}
