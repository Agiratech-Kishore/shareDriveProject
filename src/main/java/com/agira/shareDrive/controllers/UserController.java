package com.agira.shareDrive.controllers;

import com.agira.shareDrive.dtos.userDto.UserRequestDto;
import com.agira.shareDrive.dtos.userDto.UserResponseDto;
import com.agira.shareDrive.repositories.UserRepository;
import com.agira.shareDrive.services.UserService;
import com.agira.shareDrive.utility.UserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.createUser(userRequestDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> userResponseDtos = userService.getAllUsers();
        return ResponseEntity.ok(userResponseDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable int id) {
       UserResponseDto userResponseDto = userService.findUserById(id);
        return ResponseEntity.ok(userResponseDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable int id, @Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.updateUser(id, userRequestDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
