package com.agira.shareDrive.dtos.loginLogout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class LoginResponseDto {
        String message;
//    Boolean status;
    private String token;
    private String type="Bearer";

//    public LoginResponseDto(String loginSuccess, String s) {
//    }


    public LoginResponseDto(String token,String message) {
        this.token = token;
        this.message = message;
    }
}
