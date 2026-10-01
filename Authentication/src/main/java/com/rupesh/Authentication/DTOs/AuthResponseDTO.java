package com.rupesh.Authentication.DTOs;

import com.rupesh.Authentication.Entity.User;
import com.rupesh.Authentication.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthResponseDTO {

    private String accessToken;
    private String refreshToken;
    private UUID userId;
    private String username;
    private String email;
    private Role role;
}
