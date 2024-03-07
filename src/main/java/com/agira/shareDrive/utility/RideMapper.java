package com.agira.shareDrive.utility;

import com.agira.shareDrive.dtos.rideDto.RideRequestDto;
import com.agira.shareDrive.dtos.rideDto.RideResponseDto;
import com.agira.shareDrive.dtos.userDto.UserResponseDto;
import com.agira.shareDrive.entities.Ride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class RideMapper {
    @Autowired
    private UserMapper userMapper;
    public Ride rideRequestDtoToRide(@RequestBody RideRequestDto rideRequestDto){
       Ride ride = new Ride();
       ride.setDate(rideRequestDto.getDate());
       ride.setTime(rideRequestDto.getTime());
       ride.setOrigin(rideRequestDto.getOrigin());
       ride.setDestination(rideRequestDto.getDestination());
       ride.setNoOfPassengers(rideRequestDto.getPassengerCount());
       return ride;
    }

    public RideResponseDto rideToRideResponseDto(Ride ride){
       RideResponseDto rideResponseDto = new RideResponseDto();
       rideResponseDto.setId(ride.getId());
       rideResponseDto.setDate(ride.getDate());
       rideResponseDto.setOrigin(String.valueOf(ride.getOrigin()));
       rideResponseDto.setDestination(String.valueOf(ride.getDestination()));
       rideResponseDto.setPassengerCount(ride.getNoOfPassengers());
       rideResponseDto.setTime(ride.getTime());
       rideResponseDto.setPostedBy(userMapper.userToUserResponseDto(ride.getDriver()));
       return rideResponseDto;
    }
}

