package com.agira.shareDrive.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String make;
    private String model;
    private int capacity;
    @Column(unique = true)
    private String licensePlate;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
    private boolean deleted;
}
