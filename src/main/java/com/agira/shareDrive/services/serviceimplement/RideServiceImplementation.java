package com.agira.shareDrive.services.serviceimplement;

import com.agira.shareDrive.dtos.rideDto.RideRequestDto;
import com.agira.shareDrive.dtos.rideDto.RideRequestResponseDto;
import com.agira.shareDrive.dtos.rideDto.RideResponseDto;
import com.agira.shareDrive.dtos.userDto.UserResponseDto;
import com.agira.shareDrive.exceptions.RideNotFoundException;
import com.agira.shareDrive.exceptions.RideRequestNotFoundException;
import com.agira.shareDrive.exceptions.UserNotFoundException;
import com.agira.shareDrive.model.Approval;
import com.agira.shareDrive.model.Ride;
import com.agira.shareDrive.model.RideRequest;
import com.agira.shareDrive.model.User;
import com.agira.shareDrive.repositories.RideRepository;
import com.agira.shareDrive.repositories.RideRequestRepository;
import com.agira.shareDrive.services.service.RideService;
import com.agira.shareDrive.utility.RideMapper;
import com.agira.shareDrive.utility.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class RideServiceImplementation implements RideService {
    @Autowired
    private RideMapper rideMapper;
    @Autowired
    private UserServiceImplementation userService;
    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private RideRequestRepository rideRequestRepository;
    @Autowired
    private UserMapper userMapper;

    public RideResponseDto createRide(RideRequestDto rideRequestDto) throws UserNotFoundException, HttpRequestMethodNotSupportedException {
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

    public RideResponseDto getRideById(Integer id) throws RideNotFoundException {
        Optional<Ride> rideOptional = rideRepository.findById(id);
        if (rideOptional.isPresent()) {
            Ride ride = rideOptional.get();
            return rideMapper.rideToRideResponseDto(ride);
        } else {
            throw new RideNotFoundException("Ride not found with id: " + id);
        }
    }

    public RideResponseDto updateRide(Integer id, RideRequestDto rideRequestDto) throws UserNotFoundException, RideNotFoundException {
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
            throw new RideNotFoundException("Ride not found with id: " + id);
        }
    }

    @Override
    public void deleteRide(Integer id) throws RideNotFoundException {
        Optional<Ride> existingRideOptional = rideRepository.findById(id);
        if (existingRideOptional.isEmpty()) {
            throw new RideNotFoundException("Ride not found with id: " + id);
        }
        rideRepository.deleteById(id);
    }

    public List<RideResponseDto> getRideByOriginAndDestination(String origin, String destination) {
        return rideRepository.findByOriginEqualsAndDestinationEquals(origin, destination).stream().map(ride -> {
            Ride ride1 = ride.get();
            RideResponseDto rideResponseDto = new RideResponseDto();
            return rideMapper.rideToRideResponseDto(ride1);
        }).collect(Collectors.toList());
    }

    public RideRequestResponseDto createRideRequest(int userId, int rideId) throws UserNotFoundException, RideNotFoundException {
        User user = userService.getUserById(userId);
        Optional<Ride> rideOptional = rideRepository.findById(rideId);
        if (rideOptional.isEmpty()) {
            throw new RideNotFoundException("Ride not found with id: " + id);
        }
        Ride ride = rideOptional.get();
        RideRequest rideRequest = new RideRequest();
        rideRequest.setRequester(user);
        rideRequest.setRide(ride);
        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);
        ride.getRideRequests().add(savedRideRequest);
        rideRepository.save(ride);
        RideResponseDto rideResponseDto = rideMapper.rideToRideResponseDto(savedRideRequest.getRide());
        UserResponseDto userResponseDto = userMapper.userToUserResponseDto(savedRideRequest.getRequester());
        RideRequestResponseDto rideRequestResponseDto = new RideRequestResponseDto();
        rideRequestResponseDto.setRideDetails(rideResponseDto);
        rideRequestResponseDto.setUserDetails(userResponseDto);
        rideRequestResponseDto.setId(savedRideRequest.getId());
        rideRequestResponseDto.setStatus(savedRideRequest.getStatus());
        return rideRequestResponseDto;
    }

    public List<RideRequestResponseDto> getAllRideRequest(int userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        List<RideRequest> rideRequests = user.getRideRequests();
        return rideRequests.stream().map(rideRequest -> {
                    RideResponseDto rideResponseDto = rideMapper.rideToRideResponseDto(rideRequest.getRide());
                    UserResponseDto userResponseDto = userMapper.userToUserResponseDto(rideRequest.getRequester());
                    return RideRequestResponseDto.builder().
                            rideDetails(rideResponseDto).
                            userDetails(userResponseDto).
                            status(rideRequest.getStatus()).
                            id(rideRequest.getId()).build();
                })
                .collect(Collectors.toList());
    }

    public RideRequestResponseDto acceptOrDenyRideRequest(Integer id, String approval) throws RideRequestNotFoundException {
        Optional<RideRequest> rideRequestOptional = rideRequestRepository.findById(id);
        if (rideRequestOptional.isEmpty()) {
            throw new RideRequestNotFoundException("No Ride found with id: " + id);
        }
        RideRequest rideRequest = rideRequestOptional.get();
        if (approval.equalsIgnoreCase("Accept")) {
            rideRequest.setStatus(Approval.ACCEPT);
            Ride ride = rideRequest.getRide();
            if (ride.getAvailableSeats() > 0) {
                ride.setAvailableSeats(ride.getAvailableSeats() - 1);
                rideRepository.save(ride);
            } else {
                throw new RuntimeException("Seats Are filled");
            }
        } else {
            rideRequest.setStatus(Approval.REJECT);
        }
        RideRequest modifiedRideRequest = rideRequestRepository.save(rideRequest);
        RideResponseDto rideResponseDto = rideMapper.rideToRideResponseDto(modifiedRideRequest.getRide());
        UserResponseDto userResponseDto = userMapper.userToUserResponseDto(modifiedRideRequest.getRequester());
        return RideRequestResponseDto.builder()
                .id(modifiedRideRequest.getId())
                .status(modifiedRideRequest.getStatus())
                .userDetails(userResponseDto)
                .rideDetails(rideResponseDto)
                .build();
    }
}
