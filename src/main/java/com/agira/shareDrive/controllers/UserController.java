package com.agira.shareDrive.controllers;

import com.agira.shareDrive.dtos.loginLogout.LoginRequestDto;
import com.agira.shareDrive.dtos.loginLogout.LoginResponseDto;
import com.agira.shareDrive.dtos.userDto.UserRequestDto;
import com.agira.shareDrive.dtos.userDto.UserResponseDto;
import com.agira.shareDrive.exceptions.UserNotFoundException;
import com.agira.shareDrive.repositories.UserRepository;
import com.agira.shareDrive.services.serviceimplement.UserServiceImplementation;
import com.agira.shareDrive.utility.UserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${api.version}/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserServiceImplementation userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) throws Exception{
        UserResponseDto userResponseDto = userService.createUser(userRequestDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() throws UserNotFoundException {
        List<UserResponseDto> userResponseDtos = userService.getAllUsers();
        return ResponseEntity.ok(userResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable int id) throws UserNotFoundException {
        UserResponseDto userResponseDto = userService.findUserById(id);
        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable int id, @Valid @RequestBody UserRequestDto userRequestDto) throws UserNotFoundException {
        UserResponseDto userResponseDto = userService.updateUser(id, userRequestDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) throws UserNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser(@RequestBody LoginRequestDto loginRequestDto) throws UserNotFoundException {
        LoginResponseDto loginResponseDto = userService.loginUser(loginRequestDto);
        return ResponseEntity.ok(loginResponseDto);
    }
}
