package com.agira.shareDrive.services.serviceimplement;

import com.agira.shareDrive.appconfig.TokenProvider;
import com.agira.shareDrive.dtos.loginLogout.LoginRequestDto;
import com.agira.shareDrive.dtos.loginLogout.LoginResponseDto;
import com.agira.shareDrive.dtos.userDto.UserRequestDto;
import com.agira.shareDrive.dtos.userDto.UserResponseDto;
import com.agira.shareDrive.exceptions.UserNotFoundException;
import com.agira.shareDrive.model.User;
import com.agira.shareDrive.repositories.UserRepository;
import com.agira.shareDrive.services.service.UserService;
import com.agira.shareDrive.utility.UserMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private JavaMailSender javaMailSender;

    public UserResponseDto createUser(UserRequestDto userRequestDto) throws Exception {
        User user = UserMapper.userRequestDtoToUser(userRequestDto);
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        user.setPassword(encodedPassword);
        User savedUser = userRepository.save(user);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Welcome to Agira share drive");
        String mailTemplate = String.format("Dear %s,\n\nWelcome to Agira ShareDrive!\n\n", user.getName());
        simpleMailMessage.setText(mailTemplate);
        javaMailSender.send(simpleMailMessage);
        return UserMapper.userToUserResponseDto(savedUser);
    }

    @Override
    public List<UserResponseDto> getAllUsers() throws UserNotFoundException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found");
        }
        return users.stream().map(UserMapper::userToUserResponseDto).collect(Collectors.toList());
    }

    public UserResponseDto findUserById(int id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return UserMapper.userToUserResponseDto(user);
    }

    public User getUserById(int id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public UserResponseDto updateUser(int id, UserRequestDto userRequestDto) throws UserNotFoundException {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        if(userRequestDto.getEmail() != null){
            existingUser.setEmail(userRequestDto.getEmail());
        }
        if(userRequestDto.getPassword() != null){
            String hashedPassword = passwordEncoder.encode(userRequestDto.getPassword());
            existingUser.setPassword(hashedPassword);
        }
        if(userRequestDto.getName() != null){
            existingUser.setName(userRequestDto.getName());
        }
        if(userRequestDto.getMobileNumber() !=null){
            existingUser.setMobileNumber(userRequestDto.getMobileNumber());
        }
        if(userRequestDto.getAge() !=null){
            existingUser.setAge(userRequestDto.getAge());
        }
        User savedUser = userRepository.save(existingUser);
        return UserMapper.userToUserResponseDto(savedUser);
    }

    @Override
    public void deleteUser(int id) throws UserNotFoundException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        userRepository.delete(existingUser);
    }

    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) throws UserNotFoundException {
        Authentication authentication = null;
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        String token = tokenProvider.generateToken(authentication);
        String message = "Login Successful";
        UserResponseDto userByEmail = getUserByEmail(loginRequestDto.getEmail());
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setMessage(message);
        loginResponseDto.setToken(token);
        loginResponseDto.setUserId(userByEmail.getId());
        return loginResponseDto;
    }

    public UserResponseDto getUserByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new UserNotFoundException("User not found with email: " + email);
        }
        return UserMapper.userToUserResponseDto(user.get());
    }
}