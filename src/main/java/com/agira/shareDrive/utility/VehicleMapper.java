package com.agira.shareDrive.utility;

import com.agira.shareDrive.dtos.vehicleDto.VehicleRequestDto;
import com.agira.shareDrive.dtos.vehicleDto.VehicleResponseDto;
import com.agira.shareDrive.model.Vehicle;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class VehicleMapper {
    public Vehicle vehicleRequestDtoToVehicle(@RequestBody VehicleRequestDto vehicleRequestDto){
        Vehicle vehicle = new Vehicle();
        vehicle.setMake(vehicleRequestDto.getMake());
        vehicle.setModel(vehicleRequestDto.getModel());
        vehicle.setCapacity(vehicleRequestDto.getCapacity());
        vehicle.setLicensePlate(vehicleRequestDto.getLicensePlate());
        return vehicle;
    }
    public VehicleResponseDto vehicleToVehicleResponseDto(Vehicle vehicle){
        VehicleResponseDto vehicleResponseDto = new VehicleResponseDto();
        vehicleResponseDto.setId(vehicle.getId());
        vehicleResponseDto.setMake(vehicle.getMake());
        vehicleResponseDto.setModel(vehicle.getModel());
        vehicleResponseDto.setCapacity(vehicle.getCapacity());
        vehicleResponseDto.setLicensePlate(vehicle.getLicensePlate());
        return vehicleResponseDto;
    }
}
