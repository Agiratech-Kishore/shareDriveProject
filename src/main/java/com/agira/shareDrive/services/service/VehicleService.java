package com.agira.shareDrive.services.service;

import com.agira.shareDrive.dtos.vehicleDto.VehicleRequestDto;
import com.agira.shareDrive.dtos.vehicleDto.VehicleResponseDto;
import com.agira.shareDrive.exceptions.UserNotFoundException;

public interface VehicleService {

    VehicleResponseDto createVehicle(VehicleRequestDto vehicleRequestDto) throws UserNotFoundException;

    VehicleResponseDto getVehicleById(Integer id);

    VehicleResponseDto updateVehicle(Integer id, VehicleRequestDto vehicleRequestDto) throws UserNotFoundException;

    void deleteVehicle(Integer id);
}
