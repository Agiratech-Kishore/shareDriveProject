package com.agira.shareDrive.controllers;

import com.agira.shareDrive.dtos.vehicleDto.VehicleRequestDto;
import com.agira.shareDrive.dtos.vehicleDto.VehicleResponseDto;
import com.agira.shareDrive.exceptions.UserNotFoundException;
import com.agira.shareDrive.services.serviceimplement.VehicleServiceImplementation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
@CrossOrigin(origins = "http://localhost:4200")

public class VehicleController {

    @Autowired
    private VehicleServiceImplementation vehicleServiceImplementation;

    @PostMapping
    public ResponseEntity<VehicleResponseDto> createVehicle(@Valid @RequestBody VehicleRequestDto vehicleRequestDto) throws UserNotFoundException {
        VehicleResponseDto vehicleResponseDto = vehicleServiceImplementation.createVehicle(vehicleRequestDto);
        return ResponseEntity.ok(vehicleResponseDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> getVehicleById(@PathVariable Integer id) {
        VehicleResponseDto vehicleResponseDto = vehicleServiceImplementation.getVehicleById(id);
        return ResponseEntity.ok(vehicleResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> updateVehicle(@PathVariable Integer id, @Valid @RequestBody VehicleRequestDto vehicleRequestDto) throws UserNotFoundException {
        VehicleResponseDto vehicleResponseDto = vehicleServiceImplementation.updateVehicle(id, vehicleRequestDto);
        return ResponseEntity.ok(vehicleResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Integer id) {
        vehicleServiceImplementation.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}