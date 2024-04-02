package com.agira.shareDrive.controllers;

import com.agira.shareDrive.dtos.rideDto.RideRequestDto;
import com.agira.shareDrive.dtos.rideDto.RideRequestResponseDto;
import com.agira.shareDrive.dtos.rideDto.RideResponseDto;
import com.agira.shareDrive.exceptions.RideNotFoundException;
import com.agira.shareDrive.exceptions.UserNotFoundException;
import com.agira.shareDrive.services.serviceimplement.RideServiceImplementation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.version}/ride")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RideController {
    @Autowired
    private RideServiceImplementation rideServiceImplementation;

    @PostMapping
    public ResponseEntity<RideResponseDto> createRide(@Valid @RequestBody RideRequestDto rideRequestDto) throws UserNotFoundException, HttpRequestMethodNotSupportedException {
        RideResponseDto rideResponseDto = rideServiceImplementation.createRide(rideRequestDto);
        return ResponseEntity.ok(rideResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<RideResponseDto>> getAllRides(String authorization) {
        List<RideResponseDto> rides = rideServiceImplementation.getAllRides();
        return ResponseEntity.ok(rides);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideResponseDto> getRideById(@PathVariable Integer id) throws RideNotFoundException {
        RideResponseDto rideResponseDto = rideServiceImplementation.getRideById(id);
        return ResponseEntity.ok(rideResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RideResponseDto> updateRide(@PathVariable Integer id, @Valid @RequestBody RideRequestDto rideRequestDto) throws UserNotFoundException, RideNotFoundException {
        RideResponseDto rideResponseDto = rideServiceImplementation.updateRide(id, rideRequestDto);
        return ResponseEntity.ok(rideResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRide(@PathVariable Integer id) throws RideNotFoundException {
        rideServiceImplementation.deleteRide(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{origin}/{destination}")
    public ResponseEntity<List<RideResponseDto>> getRidesByOriginAndDestination(@PathVariable String origin, @PathVariable String destination) {
        List<RideResponseDto> rideByOriginAndDestination = rideServiceImplementation.getRideByOriginAndDestination(origin, destination);
        return ResponseEntity.ok(rideByOriginAndDestination);
    }

    @PostMapping("/request")
    public ResponseEntity<RideRequestResponseDto> createRideRequest(@RequestParam int user, @RequestParam int ride) throws UserNotFoundException, RideNotFoundException {
        RideRequestResponseDto rideRequestResponseDto = rideServiceImplementation.createRideRequest(user, ride);
        return ResponseEntity.ok(rideRequestResponseDto);
    }

    @GetMapping("/ride-requests/user/{userId}")
    public ResponseEntity<List<RideRequestResponseDto>> getAllRideRequests(@PathVariable("userId") int userId) throws UserNotFoundException {
        List<RideRequestResponseDto> rideRequestResponseDtos = rideServiceImplementation.getAllRideRequest(userId);
        return new ResponseEntity<>(rideRequestResponseDtos, HttpStatus.OK);
    }

    @PatchMapping("ride-request/{rideRequestId}")
    public ResponseEntity<RideRequestResponseDto> acceptOrRejectRideRequest(@PathVariable int rideRequestId, @RequestParam String approval) throws RuntimeException {
        RideRequestResponseDto rideRequestResponseDto = rideServiceImplementation.acceptOrDenyRideRequest(rideRequestId, approval);
        return new ResponseEntity<>(rideRequestResponseDto, HttpStatus.OK);
    }

}
