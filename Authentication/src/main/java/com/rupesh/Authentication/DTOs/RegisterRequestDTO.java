package com.rupesh.Authentication.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterRequestDTO {

    private String username;
    private String email;
    private String password;
}
