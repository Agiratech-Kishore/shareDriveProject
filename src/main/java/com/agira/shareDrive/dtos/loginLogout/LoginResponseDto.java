package com.agira.shareDrive.dtos.loginLogout;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String message;
    private String token;
    private String type = "Bearer";
    private int userId;
}
