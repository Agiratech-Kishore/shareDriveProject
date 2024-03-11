package com.agira.shareDrive.dtos.userDto;

import com.agira.shareDrive.validatePassword.ValidPassword;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    @NotNull(message = "Name should not be null")
    private String name;
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email should not be blank")
    private String email;
    @NotNull(message = "Age should not be null")
    @Min(value = 1, message = "Age should be greater than 1")
    private Integer age;
    @Size(min = 10, max = 10, message = "Mobile number should be 10 digit")
    @NotBlank(message = "Mobile number should not be blank")
    private String mobileNumber;
    @NotBlank(message = "Password should not be blank")
    @ValidPassword(message = "A minimum 6 characters password contains a combination of uppercase and lowercase letter and number are required.")
    private String passWord;
}