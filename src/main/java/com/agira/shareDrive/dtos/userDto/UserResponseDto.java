package com.agira.shareDrive.dtos.userDto;

import com.agira.shareDrive.dtos.rideDto.RideResponseDto;
import com.agira.shareDrive.model.Ride;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private Integer id;
    private String name;
    private String email;
    private Integer age;
    private String mobileNumber;
    private String token;
    private List<RideResponseDto> rideList;
}
