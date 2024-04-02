package com.agira.shareDrive.services.service;

import com.agira.shareDrive.dtos.userDto.UserRequestDto;
import com.agira.shareDrive.dtos.userDto.UserResponseDto;
import com.agira.shareDrive.exceptions.UserNotFoundException;
import com.agira.shareDrive.model.User;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto) throws Exception;

    List<UserResponseDto> getAllUsers() throws UserNotFoundException;

    UserResponseDto findUserById(int id) throws UserNotFoundException;

    User getUserById(int id) throws UserNotFoundException;

    UserResponseDto updateUser(int id, UserRequestDto userRequestDto) throws UserNotFoundException;

    void deleteUser(int id) throws UserNotFoundException;


}
