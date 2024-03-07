package com.agira.shareDrive.dtos.rideDto;

import com.agira.shareDrive.dtos.userDto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private int passengerCount;
    private Date date;
    private Time time;
    private UserResponseDto postedBy;
}
