package com.agira.shareDrive.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private User driver;
    @ManyToMany(mappedBy = "rides")
    private List<User> passengers;
    private int noOfPassengers;
    private String origin;
    private String destination;
    private Date date;
    private Time time;
    @OneToMany(mappedBy = "ride")
    private List<RideRequest> rideRequests;
    @OneToMany(mappedBy = "ride")
    private List<RideParticipation> rideParticipations;
    private boolean deleted;
}
