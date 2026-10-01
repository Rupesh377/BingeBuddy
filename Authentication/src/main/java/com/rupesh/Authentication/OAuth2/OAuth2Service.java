package com.rupesh.Authentication.OAuth2;

import com.rupesh.Authentication.DTOs.AuthResponseDTO;
import com.rupesh.Authentication.DTOs.LoginResponseDTO;
import com.rupesh.Authentication.Entity.RefreshToken;
import com.rupesh.Authentication.Entity.User;
import com.rupesh.Authentication.Enum.AuthProvider;
import com.rupesh.Authentication.Enum.Role;
import com.rupesh.Authentication.Repository.RefreshTokenRepository;
import com.rupesh.Authentication.Repository.UserRepository;
import com.rupesh.Authentication.Service.JwtService;
import com.rupesh.Authentication.Service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OAuth2Service {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;

    public AuthResponseDTO login(String email, String name, AuthProvider provider)
    {
        User user;
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            user = existingUser.get();

            if (user.getProvider() != provider) {
                throw new RuntimeException("This email is already registered with " + user.getProvider());
            }
        } else {
            user = User.builder()
                    .username(name)
                    .email(email)
                    .provider(provider)
                    .role(Role.USER)
                    .build();
            userRepository.save(user);

        }

        refreshTokenRepository.deleteByUser(user);
        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.CreateRefreshToken(user);

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
