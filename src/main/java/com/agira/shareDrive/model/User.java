package com.agira.shareDrive.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String email;
    private Integer age;
    @Column(unique = true)
    private String mobileNumber;
    private String password;
    @OneToMany(mappedBy = "driver", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Ride> ridesAsDriver = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY ,orphanRemoval = true)
    private List<Vehicle> vehicles = new ArrayList<>();
    @OneToMany(mappedBy = "requester", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    @JsonManagedReference
    private List<RideRequest> rideRequests = new ArrayList<>();
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Ride> rides = new ArrayList<>();
    private boolean deleted;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Role> roleList = new ArrayList<>();
}
