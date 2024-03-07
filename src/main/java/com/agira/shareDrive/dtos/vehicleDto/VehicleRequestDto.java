package com.agira.shareDrive.dtos.vehicleDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequestDto {
    @NotBlank(message = "Make should not be blank")
    @Size(max = 15, message = "Make should be at most 15 characters")
    private String make;
    @NotBlank(message = "Model should not be blank")
    @Size(max = 15, message = "Model should be at most 15 characters")
    private String model;

    @NotNull(message = "Capacity should not be null")
    @Min(value = 1, message = "Capacity should be a positive integer")
    private int capacity;

    @NotBlank(message = "License plate should not be blank")
    @Size(max = 10, message = "License plate should be at most 10 characters")
    private String licensePlate;

    @NotNull(message = "User ID should not be null")
    private int userId;
}