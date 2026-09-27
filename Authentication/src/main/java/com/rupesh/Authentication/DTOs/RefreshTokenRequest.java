package com.rupesh.Authentication.DTOs;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RefreshTokenRequest {

    @NotBlank(message = "refreshtoken is required")
    private String refreshToken;
}
