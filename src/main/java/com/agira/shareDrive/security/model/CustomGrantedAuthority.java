package com.agira.shareDrive.security.model;

import com.agira.shareDrive.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@NoArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {
    private String role;

    CustomGrantedAuthority(Role role){
        this.role = role.getName();
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
