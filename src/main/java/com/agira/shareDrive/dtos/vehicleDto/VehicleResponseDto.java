package com.agira.shareDrive.dtos.vehicleDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponseDto {
    private Integer id;
    private String make;
    private String model;
    private int capacity;
    private String licensePlate;
}
