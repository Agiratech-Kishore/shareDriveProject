package com.agira.shareDrive.dtos.rideDto;

import com.agira.shareDrive.dtos.userDto.UserResponseDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideRequestResponseDto {
    private Integer id;
    private String status;
    private UserResponseDto userDetails;
    private RideResponseDto rideDetails;
}