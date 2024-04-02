package com.agira.shareDrive.dtos.rideDto;

import com.agira.shareDrive.dtos.userDto.UserResponseDto;
import com.agira.shareDrive.model.RideRequest;
import com.agira.shareDrive.model.User;
import com.agira.shareDrive.statusconstants.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RideResponseDto {
    private int id;
    private String origin;
    private String destination;
    private int availableSeats;
    private LocalDate date;
    private LocalTime time;
    private String driverName;
    private int driverID;
    private String rideStatus;
}
