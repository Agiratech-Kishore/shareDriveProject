package com.agira.shareDrive.services.serviceimplement;

import com.agira.shareDrive.dtos.vehicleDto.VehicleRequestDto;
import com.agira.shareDrive.dtos.vehicleDto.VehicleResponseDto;
import com.agira.shareDrive.exceptions.UserNotFoundException;
import com.agira.shareDrive.model.User;
import com.agira.shareDrive.model.Vehicle;
import com.agira.shareDrive.repositories.VehicleRepository;
import com.agira.shareDrive.services.service.VehicleService;
import com.agira.shareDrive.utility.VehicleMapper;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImplementation implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleMapper vehicleMapper;

    @Autowired
    private UserServiceImplementation userService;

    public VehicleResponseDto createVehicle(VehicleRequestDto vehicleRequestDto) throws UserNotFoundException {
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

    public VehicleResponseDto updateVehicle(Integer id, VehicleRequestDto vehicleRequestDto) throws UserNotFoundException {
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
            Vehicle vehicle = existingVehicleOptional.get();
            vehicle.setDeleted(true);
            vehicleRepository.save(vehicle);
        } else {
            throw new RuntimeException("Vehicle not found with id: " + id);
        }
    }

    @Override
    public List<VehicleResponseDto> getVehicleByUser(Integer userId) {
        List<Vehicle> vehicleList = vehicleRepository.findByUserIdAndDeletedEquals(userId, false).orElseThrow(() -> new RuntimeException("Vehicle not found with this id" + userId));
        return vehicleList.stream()
                .map(vehicle -> vehicleMapper.vehicleToVehicleResponseDto(vehicle)).toList();
    }

}
