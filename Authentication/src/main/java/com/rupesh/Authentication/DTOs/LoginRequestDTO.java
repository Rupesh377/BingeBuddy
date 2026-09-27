package com.rupesh.Authentication.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginRequestDTO {

    private String username;
    private String email;
    private String password;
}
