package com.agira.shareDrive.controllers;

import com.agira.shareDrive.dtos.vehicleDto.VehicleRequestDto;
import com.agira.shareDrive.dtos.vehicleDto.VehicleResponseDto;
import com.agira.shareDrive.services.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponseDto> createVehicle(@Valid @RequestBody VehicleRequestDto vehicleRequestDto) {
        VehicleResponseDto vehicleResponseDto = vehicleService.createVehicle(vehicleRequestDto);
        return ResponseEntity.ok(vehicleResponseDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> getVehicleById(@PathVariable Integer id) {
        VehicleResponseDto vehicleResponseDto = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(vehicleResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> updateVehicle(@PathVariable Integer id, @Valid @RequestBody VehicleRequestDto vehicleRequestDto) {
        VehicleResponseDto vehicleResponseDto = vehicleService.updateVehicle(id, vehicleRequestDto);
        return ResponseEntity.ok(vehicleResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Integer id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}