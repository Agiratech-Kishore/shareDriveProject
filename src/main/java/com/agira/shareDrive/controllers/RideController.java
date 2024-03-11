package com.agira.shareDrive.controllers;

import com.agira.shareDrive.dtos.rideDto.RideRequestDto;
import com.agira.shareDrive.dtos.rideDto.RideRequestResponseDto;
import com.agira.shareDrive.dtos.rideDto.RideResponseDto;
import com.agira.shareDrive.services.RideService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ride")
public class RideController {
    @Autowired
    private RideService rideService;
    @PostMapping
    public ResponseEntity<RideResponseDto> createRide(@Valid @RequestBody RideRequestDto rideRequestDto){
        RideResponseDto rideResponseDto = rideService.createRide(rideRequestDto);
        return ResponseEntity.ok(rideResponseDto);
    }
    @GetMapping
    public ResponseEntity<List<RideResponseDto>> getAllRides() {
        List<RideResponseDto> rides = rideService.getAllRides();
        return ResponseEntity.ok(rides);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RideResponseDto> getRideById(@PathVariable Integer id) {
        RideResponseDto rideResponseDto = rideService.getRideById(id);
        return ResponseEntity.ok(rideResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RideResponseDto> updateRide(@PathVariable Integer id, @Valid @RequestBody RideRequestDto rideRequestDto) {
        RideResponseDto rideResponseDto = rideService.updateRide(id, rideRequestDto);
        return ResponseEntity.ok(rideResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRide(@PathVariable Integer id) {
        rideService.deleteRide(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{origin}/{destination}")
    public ResponseEntity<List<RideResponseDto>> getRidesByOriginAndDestination(@PathVariable String origin, @PathVariable String destination){
        List<RideResponseDto> rideByOriginAndDestination = rideService.getRideByOriginAndDestination(origin, destination);
        return ResponseEntity.ok(rideByOriginAndDestination);
    }
    @PostMapping("/request")
    public ResponseEntity<RideRequestResponseDto> createRideRequest(@RequestParam int user,@RequestParam int ride){
        RideRequestResponseDto rideRequestResponseDto = rideService.createRideRequest(user, ride);
        return ResponseEntity.ok(rideRequestResponseDto);
    }
    @GetMapping("/ride-requests/{userId}")
    public ResponseEntity<List<RideRequestResponseDto>> getAllRideRequests(@PathVariable("userId") int userId) {
            List<RideRequestResponseDto> rideRequestResponseDtos = rideService.getAllRideRequest(userId);
            return new ResponseEntity<>(rideRequestResponseDtos, HttpStatus.OK);
    }

    @PatchMapping("/{rideId}/{approval}")
    public ResponseEntity<RideRequestResponseDto> acceptOrRejectRideRequest(@PathVariable int rideId, @PathVariable String approval){
        RideRequestResponseDto rideRequestResponseDto = rideService.acceptOrDenyRideRequest(rideId, approval);
        return new ResponseEntity<>(rideRequestResponseDto, HttpStatus.OK);
    }
}
