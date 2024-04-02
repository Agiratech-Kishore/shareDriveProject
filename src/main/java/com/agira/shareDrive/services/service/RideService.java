package com.agira.shareDrive.services.service;

import com.agira.shareDrive.dtos.rideDto.RideRequestDto;
import com.agira.shareDrive.dtos.rideDto.RideRequestResponseDto;
import com.agira.shareDrive.dtos.rideDto.RideResponseDto;
import com.agira.shareDrive.exceptions.RideNotFoundException;
import com.agira.shareDrive.exceptions.RideRequestNotFoundException;
import com.agira.shareDrive.exceptions.UserNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.List;

public interface RideService {
    RideResponseDto createRide(RideRequestDto rideRequestDto) throws UserNotFoundException, HttpRequestMethodNotSupportedException;

    List<RideResponseDto> getAllRides();

    RideResponseDto getRideById(Integer id) throws RideNotFoundException;

    RideResponseDto updateRide(Integer id, RideRequestDto rideRequestDto) throws UserNotFoundException, RideNotFoundException;

    void deleteRide(Integer id) throws RideNotFoundException;

    List<RideResponseDto> getRideByOriginAndDestination(String origin, String destination);

    RideRequestResponseDto createRideRequest(int userId, int rideId) throws UserNotFoundException, RideNotFoundException;

    List<RideRequestResponseDto> getAllRideRequest(int userId) throws UserNotFoundException;

    RideRequestResponseDto acceptOrDenyRideRequest(Integer id, String approval) throws RideRequestNotFoundException;

    RideResponseDto completeOrCancelRide(Integer rideId,String status) throws RideNotFoundException;
}
