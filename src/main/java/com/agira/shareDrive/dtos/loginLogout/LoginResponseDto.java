package com.agira.shareDrive.dtos.loginLogout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    String message;
    Boolean status;

    public LoginResponseDto(String loginSuccess, String s) {
    }
}
