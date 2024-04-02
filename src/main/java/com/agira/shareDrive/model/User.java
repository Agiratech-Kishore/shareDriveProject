package com.agira.shareDrive.model;

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
    private List<Ride> ridesAsDriver;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private List<Vehicle> vehicles;
    @OneToMany(mappedBy = "requester", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    @JsonManagedReference
    private List<RideRequest> rideRequests = new ArrayList<>();
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Ride> rides = new ArrayList<>();
    private boolean deleted;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Role> roleList;
}
