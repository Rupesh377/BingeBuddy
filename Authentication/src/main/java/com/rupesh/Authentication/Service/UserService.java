package com.rupesh.Authentication.Service;

import com.rupesh.Authentication.DTOs.*;
import com.rupesh.Authentication.Entity.RefreshToken;
import com.rupesh.Authentication.Entity.User;
import com.rupesh.Authentication.Enum.AuthProvider;
import com.rupesh.Authentication.Enum.Role;
import com.rupesh.Authentication.Repository.RefreshTokenRepository;
import com.rupesh.Authentication.Repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final ForgetPasswordService forgetPasswordService;
    private final RefreshTokenRepository refreshTokenRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService service, RefreshTokenService refreshTokenService, ForgetPasswordService forgetPasswordService, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = service;
        this.refreshTokenService = refreshTokenService;
        this.forgetPasswordService = forgetPasswordService;
        this.refreshTokenRepository = refreshTokenRepository;
    }


    public String CreateAccount(RegisterRequestDTO registerRequestDTO) {

        if(userRepository.existsByEmail(registerRequestDTO.getEmail())){
           throw new RuntimeException("User Already Exists with this Email");
        }
        User user=User.builder()
                .username(registerRequestDTO.getUsername())
                .email(registerRequestDTO.getEmail().toLowerCase())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .role(Role.USER)
                .provider(AuthProvider.LOCAL)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return "User Created Successfully";
    }

    public LoginResponseDTO UserLogin(LoginRequestDTO loginRequestDTO) {

        User user=userRepository.findByEmail(loginRequestDTO.getEmail().toLowerCase()).orElseThrow(
                ()->new RuntimeException("Register your account first"));
        if(user.getProvider()!= AuthProvider.LOCAL)
        {
            throw new RuntimeException("Login with: "+user.getProvider());
        }

        if(!passwordEncoder.encode(loginRequestDTO.getPassword()).equals(user.getPassword()))
        {
            throw new RuntimeException("Invalid Email or Password");
        }
        String accessToken=jwtService.generateAccessToken(user);
        String refreshToken= jwtService.generateRefreshToken(user);

        return new LoginResponseDTO( user.getEmail(), accessToken , refreshToken);
    }

    public AuthResponseDTO refreshToken(RefreshTokenRequest refreshTokenRequest) {

        RefreshToken refreshToken=refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user=refreshToken.getUser();
        String accesstoken= jwtService.generateAccessToken(user);
        String refreshtoken=jwtService.generateRefreshToken(user);

        return AuthResponseDTO.builder()
                .accessToken(accesstoken)
                .refreshToken(refreshtoken)
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public void logout(User user) {
        refreshTokenService.deleteRefreshToken(user);
    }
}
