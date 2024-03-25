package com.agira.shareDrive.utility;

import com.agira.shareDrive.dtos.rideDto.RideResponseDto;
import com.agira.shareDrive.dtos.userDto.UserRequestDto;
import com.agira.shareDrive.dtos.userDto.UserResponseDto;
import com.agira.shareDrive.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {


    public User userRequestDtoToUser(@RequestBody UserRequestDto userRequestDto) {
        User user = new User();
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setAge(userRequestDto.getAge());
        user.setMobileNumber(userRequestDto.getMobileNumber());
        return user;
    }

    public UserResponseDto userToUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setAge(user.getAge());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setMobileNumber(user.getMobileNumber());
        List<RideResponseDto> rideResponseDtoList = user.getRides().stream().map(ride -> RideMapper.rideToRideResponseDto(ride)).collect(Collectors.toList());
        userResponseDto.setRideList(rideResponseDtoList);
        return userResponseDto;
    }
}
