package com.rupesh.Authentication.Controller;

import com.rupesh.Authentication.DTOs.*;
import com.rupesh.Authentication.Security.CustomUserDetails;
import com.rupesh.Authentication.Service.ForgetPasswordService;
import com.rupesh.Authentication.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ForgetPasswordService forgetPasswordService;


    @PostMapping("/create")
    public ResponseEntity<String> Register(@RequestBody RegisterRequestDTO registerRequestDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.CreateAccount(registerRequestDTO));
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        return ResponseEntity.ok(userService.UserLogin(loginRequestDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest)
    {
        return ResponseEntity.ok(userService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        userService.logout(userDetails.getUser());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword( @RequestBody ForgetPasswordRequest request) {
        forgetPasswordService.forgotPassword(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword( @RequestBody ResetPasswordRequest request) {
        forgetPasswordService.resetPassword(request);
        return ResponseEntity.ok().build();
    }
}
