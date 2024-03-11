package com.agira.shareDrive.services;

import com.agira.shareDrive.dtos.loginLogout.LoginRequestDto;
import com.agira.shareDrive.dtos.loginLogout.LoginResponseDto;
import com.agira.shareDrive.dtos.userDto.UserRequestDto;
import com.agira.shareDrive.dtos.userDto.UserResponseDto;
import com.agira.shareDrive.model.User;
import com.agira.shareDrive.repositories.UserRepository;
import com.agira.shareDrive.utility.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = userMapper.userRequestDtoToUser(userRequestDto);
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassWord());
        user.setPassword(encodedPassword);
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

    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
        String msg = "";
        User user1 = userRepository.findByEmail(loginRequestDto.getEmail());
        if(user1!=null){
            String plainPassword = loginRequestDto.getPassword();
            String encodedPassword = user1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(plainPassword,encodedPassword);
            if(isPwdRight){
               Optional<User> user = userRepository.findOneByEmailAndPassword(loginRequestDto.getEmail(),loginRequestDto.getPassword());
               if(user.isPresent()){
                   return new LoginResponseDto("Login Success",true);
               } else {
                   return new LoginResponseDto("Login Failed",false);
               }
            } else {
                return new LoginResponseDto("Password Not Match",false);
            }
        } else {
            return new LoginResponseDto("Email not exists",false);
        }
    }
}