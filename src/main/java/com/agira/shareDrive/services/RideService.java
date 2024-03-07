package com.agira.shareDrive.services;

import com.agira.shareDrive.dtos.rideDto.RideRequestDto;
import com.agira.shareDrive.dtos.rideDto.RideRequestResponseDto;
import com.agira.shareDrive.dtos.rideDto.RideResponseDto;
import com.agira.shareDrive.dtos.userDto.UserResponseDto;
import com.agira.shareDrive.entities.Ride;
import com.agira.shareDrive.entities.RideRequest;
import com.agira.shareDrive.entities.User;
import com.agira.shareDrive.repositories.RideRepository;
import com.agira.shareDrive.repositories.RideRequestRepository;
import com.agira.shareDrive.utility.RideMapper;
import com.agira.shareDrive.utility.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class RideService {
    @Autowired
    private RideMapper rideMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private RideRequestRepository rideRequestRepository;
    @Autowired
    private UserMapper userMapper;
    public RideResponseDto createRide(RideRequestDto rideRequestDto) {
        User user = userService.getUserById(rideRequestDto.getUserId());
        Ride ride = rideMapper.rideRequestDtoToRide(rideRequestDto);
        ride.setDriver(user);
        Ride createdRide = rideRepository.save(ride);
        return rideMapper.rideToRideResponseDto(ride);
    }
    public List<RideResponseDto> getAllRides() {
        List<Ride> rides = rideRepository.findAll();
        return rides.stream().map(ride -> rideMapper.rideToRideResponseDto(ride)).collect(Collectors.toList());
    }

    public RideResponseDto getRideById(Integer id) {
        Optional<Ride> rideOptional = rideRepository.findById(id);
        if (rideOptional.isPresent()) {
            Ride ride = rideOptional.get();
            return rideMapper.rideToRideResponseDto(ride);
        } else {
            throw new RuntimeException("Ride not found with id: " + id);
        }
    }

    public RideResponseDto updateRide(Integer id, RideRequestDto rideRequestDto) {
        Optional<Ride> existingRideOptional = rideRepository.findById(id);
        if (existingRideOptional.isPresent()) {
            Ride existingRide = existingRideOptional.get();

            User user = userService.getUserById(rideRequestDto.getUserId());
            Ride updatedRide = rideMapper.rideRequestDtoToRide(rideRequestDto);
            updatedRide.setDriver(user);
            updatedRide.setId(existingRide.getId());

            Ride savedRide = rideRepository.save(updatedRide);
            return rideMapper.rideToRideResponseDto(savedRide);
        } else {
            throw new RuntimeException("Ride not found with id: " + id);
        }
    }

    public void deleteRide(Integer id) {
        Optional<Ride> existingRideOptional = rideRepository.findById(id);
        if (existingRideOptional.isPresent()) {
            rideRepository.deleteById(id);
        } else {
            throw new RuntimeException("Ride not found with id: " + id);
        }
    }

    public List<RideResponseDto> getRideByOriginAndDestination(String origin, String destination){
        return rideRepository.findByOriginEqualsAndDestinationEquals(origin, destination).stream().map(ride -> {
            Ride ride1 = ride.get();
            RideResponseDto rideResponseDto = new RideResponseDto();
            return rideMapper.rideToRideResponseDto(ride1);
        }).collect(Collectors.toList());
    }
    public RideRequestResponseDto createRideRequest(int userId,int rideId){
        User user = userService.getUserById(userId);
        Optional<Ride> rideOptional = rideRepository.findById(rideId);
        if(!rideOptional.isPresent()){
            throw new RuntimeException("Ride not found with id: " + id);
        }
        Ride ride = rideOptional.get();
        RideRequest rideRequest = new RideRequest();
        rideRequest.setRequester(user);
        rideRequest.setRide(ride);
        rideRequest.setStatus("Pending");
        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);
        RideResponseDto rideResponseDto = rideMapper.rideToRideResponseDto(savedRideRequest.getRide());
        UserResponseDto userResponseDto = userMapper.userToUserResponseDto(savedRideRequest.getRequester());
        RideRequestResponseDto rideRequestResponseDto = new RideRequestResponseDto();
        rideRequestResponseDto.setRideDetails(rideResponseDto);
        rideRequestResponseDto.setUserDetails(userResponseDto);
        return rideRequestResponseDto;
    }
}
