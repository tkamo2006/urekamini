package com.uplus.miniproject2.dto;

import com.uplus.miniproject2.entity.user.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDto {
    private String name;
    private String username;
    private String password;
    private String gender;
    private Role role;
}
