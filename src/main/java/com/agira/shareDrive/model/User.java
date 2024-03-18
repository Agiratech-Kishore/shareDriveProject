package com.agira.shareDrive.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User{
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
    @OneToMany(mappedBy = "driver",fetch = FetchType.EAGER)
    private List<Ride> ridesAsDriver;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user",fetch = FetchType.EAGER)
    private List<Vehicle> vehicles;
    @OneToMany(mappedBy = "requester",fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE,CascadeType.MERGE})
    private List<RideRequest> rideRequests;
    @ManyToMany
    private List<Ride> rides;
    private boolean deleted;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Role> roleList;

}