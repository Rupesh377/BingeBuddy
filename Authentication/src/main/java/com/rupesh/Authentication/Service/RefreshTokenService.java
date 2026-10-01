package com.rupesh.Authentication.Service;

import com.rupesh.Authentication.Entity.RefreshToken;
import com.rupesh.Authentication.Entity.User;
import com.rupesh.Authentication.Repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.ref.Reference;
import java.time.Instant;

@Service
public class  RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiry;

    public RefreshToken CreateRefreshToken(User user)
    {
        refreshTokenRepository.deleteByUser(user);
        String token= jwtService.generateRefreshToken(user);

        RefreshToken refreshToken=RefreshToken.builder()
                .token(token)
                .user(user)
                .expiryTime(Instant.now().plusMillis(refreshTokenExpiry))
                .revoked(false)
                .build();
        return refreshToken;
    }

    public RefreshToken verifyRefreshToken(String token)
    {
        RefreshToken refreshToken=refreshTokenRepository.findByToken(token).orElseThrow(
                ()-> new RuntimeException("Invalid Refresh token"));

        if(refreshToken.isRevoked())
            throw new RuntimeException("Refresh token has been revoked");

        if(refreshToken.getExpiryTime().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }
        return refreshToken;
    }

    public RefreshToken revokeRefreshToken(String token)
    {
        RefreshToken refreshToken=refreshTokenRepository.findByToken(token).orElseThrow(
                ()-> new RuntimeException("Refresh token not found"));
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public void deleteRefreshToken(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
}
