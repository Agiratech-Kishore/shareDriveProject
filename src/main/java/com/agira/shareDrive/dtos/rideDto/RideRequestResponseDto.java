package com.agira.shareDrive.dtos.rideDto;

import com.agira.shareDrive.dtos.userDto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestResponseDto {
    private UserResponseDto userDetails;
    private RideResponseDto rideDetails;
}