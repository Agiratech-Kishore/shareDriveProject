package com.agira.shareDrive.services;

import com.agira.shareDrive.dtos.userDto.UserRequestDto;
import com.agira.shareDrive.dtos.userDto.UserResponseDto;
import com.agira.shareDrive.entities.User;
import com.agira.shareDrive.repositories.UserRepository;
import com.agira.shareDrive.utility.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = userMapper.userRequestDtoToUser(userRequestDto);
        User savedUser = userRepository.save(user);
        return userMapper.userToUserResponseDto(savedUser);
    }
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtos = users.stream().map(user -> userMapper.userToUserResponseDto(user)).collect(Collectors.toList());
        return userResponseDtos;
    }
    public UserResponseDto findUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.userToUserResponseDto(user);
    }
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    public UserResponseDto updateUser(int id, UserRequestDto userRequestDto) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        User updatedUser = userMapper.userRequestDtoToUser(userRequestDto);
        updatedUser.setId(id);
        User savedUser = userRepository.save(updatedUser);
        return userMapper.userToUserResponseDto(savedUser);
    }

    public void deleteUser(int id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        existingUser.setDeleted(true);
        userRepository.save(existingUser);
    }
}
