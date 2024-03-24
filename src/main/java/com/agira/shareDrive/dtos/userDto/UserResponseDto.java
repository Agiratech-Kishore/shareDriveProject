package com.agira.shareDrive.dtos.userDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private Integer id;
    private String name;
    private String email;
    private Integer age;
    private String mobileNumber;
    private String token;
}
