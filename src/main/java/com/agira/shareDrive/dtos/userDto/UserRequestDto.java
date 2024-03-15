package com.agira.shareDrive.dtos.userDto;

import jakarta.persistence.Column;
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
    @Column(unique = true)
    private String email;
    @NotNull(message = "Age should not be null")
    @Min(value = 1, message = "Age should be greater than 1")
    private Integer age;
    @Size(min = 10, max = 10, message = "Mobile number should be 10 digit")
    @NotBlank(message = "Mobile number should not be blank")
    @Column(unique = true)
    private String mobileNumber;
    @NotBlank(message = "Password should not be blank")
    @NotBlank(message = "Password cannot be null or empty")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,20}$", message = "A minimum 6 characters password contains a combination of uppercase and lowercase letter and number are required.")
    private String password;
}