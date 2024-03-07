package com.agira.shareDrive.services;

import com.agira.shareDrive.dtos.vehicleDto.VehicleRequestDto;
import com.agira.shareDrive.dtos.vehicleDto.VehicleResponseDto;
import com.agira.shareDrive.entities.User;
import com.agira.shareDrive.entities.Vehicle;
import com.agira.shareDrive.repositories.VehicleRepository;
import com.agira.shareDrive.utility.VehicleMapper;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleMapper vehicleMapper;

    @Autowired
    private UserService userService;

    public VehicleResponseDto createVehicle(VehicleRequestDto vehicleRequestDto) {
        User user = userService.getUserById(vehicleRequestDto.getUserId());
        Vehicle vehicle = vehicleMapper.vehicleRequestDtoToVehicle(vehicleRequestDto);
        vehicle.setUser(user);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.vehicleToVehicleResponseDto(savedVehicle);
    }
    public VehicleResponseDto getVehicleById(Integer id) {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(id);
        if (vehicleOptional.isPresent()) {
            Vehicle vehicle = vehicleOptional.get();
            return vehicleMapper.vehicleToVehicleResponseDto(vehicle);
        } else {
            throw new RuntimeException("Vehicle not found with id: " + id);
        }
    }

    public VehicleResponseDto updateVehicle(Integer id, VehicleRequestDto vehicleRequestDto) {
        Optional<Vehicle> existingVehicleOptional = vehicleRepository.findById(id);
        if (existingVehicleOptional.isPresent()) {
            Vehicle existingVehicle = existingVehicleOptional.get();

            User user = userService.getUserById(vehicleRequestDto.getUserId());
            Vehicle updatedVehicle = vehicleMapper.vehicleRequestDtoToVehicle(vehicleRequestDto);
            updatedVehicle.setId(existingVehicle.getId());
            updatedVehicle.setUser(user);

            Vehicle savedVehicle = vehicleRepository.save(updatedVehicle);
            return vehicleMapper.vehicleToVehicleResponseDto(savedVehicle);
        } else {
            throw new RuntimeException("Vehicle not found with id: " + id);
        }
    }

    public void deleteVehicle(Integer id) {
        Optional<Vehicle> existingVehicleOptional = vehicleRepository.findById(id);
        if (existingVehicleOptional.isPresent()) {
            vehicleRepository.deleteById(id);
        } else {
            throw new RuntimeException("Vehicle not found with id: " + id);
        }
    }
}
