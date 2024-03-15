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

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private JavaMailSender javaMailSender;
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = userMapper.userRequestDtoToUser(userRequestDto);
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        user.setPassword(encodedPassword);
        User savedUser = userRepository.save(user);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Welcome to Agira share drive");
        String mailTemplate = String.format("Dear %s,\n\nWelcome to Agira ShareDrive!\n\n", user.getName());
        simpleMailMessage.setText(mailTemplate);
        javaMailSender.send(simpleMailMessage);
        return userMapper.userToUserResponseDto(savedUser);
    }

    @Override
    public List<UserResponseDto> getAllUsers() throws UserNotFoundException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found");
        }
        return users.stream().map(userMapper::userToUserResponseDto).collect(Collectors.toList());
    }

    public UserResponseDto findUserById(int id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return userMapper.userToUserResponseDto(user);
    }

    public User getUserById(int id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public UserResponseDto updateUser(int id, UserRequestDto userRequestDto) throws UserNotFoundException {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        User updatedUser = userMapper.userRequestDtoToUser(userRequestDto);
        updatedUser.setId(id);
        User savedUser = userRepository.save(updatedUser);
        return userMapper.userToUserResponseDto(savedUser);
    }

    @Override
    public void deleteUser(int id) throws UserNotFoundException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        userRepository.delete(existingUser);
    }

//    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
//        String msg = "";
//        User user1 = userRepository.findByEmail(loginRequestDto.getEmail());
//        if(user1!=null){
//            String plainPassword = loginRequestDto.getPassword();
//            String encodedPassword = user1.getPassword();
//            Boolean isPwdRight = passwordEncoder.matches(plainPassword,encodedPassword);
//            if(isPwdRight){
//               Optional<User> user = userRepository.findOneByEmailAndPassword(loginRequestDto.getEmail(),loginRequestDto.getPassword());
//               if(user.isPresent()){
//                   return new LoginResponseDto("Login Success",true);
//               } else {
//                   return new LoginResponseDto("Login Failed",false);
//               }
//            } else {
//                return new LoginResponseDto("Password Not Match",false);
//            }
//        } else {
//            return new LoginResponseDto("Email not exists",false);
//        }
//    }

    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
        Authentication authentication = null;
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));

//        return new LoginResponseDto("Login Success",tokenProvider.generateToken(authentication));
        String token = tokenProvider.generateToken(authentication);
        String message = "Login Successfull";
        return new LoginResponseDto(token, message);
    }

}