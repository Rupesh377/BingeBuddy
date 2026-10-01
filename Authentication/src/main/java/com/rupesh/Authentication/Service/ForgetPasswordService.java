package com.rupesh.Authentication.Service;

import com.rupesh.Authentication.DTOs.ForgetPasswordRequest;
import com.rupesh.Authentication.DTOs.ResetPasswordRequest;
import com.rupesh.Authentication.Entity.ForgetPasswordToken;
import com.rupesh.Authentication.Entity.User;
import com.rupesh.Authentication.Repository.ForgetPasswordTokenRepository;
import com.rupesh.Authentication.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ForgetPasswordService {

    private final UserRepository userRepository;
    private final ForgetPasswordTokenRepository forgetPasswordTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final Forget_EmailSender forgetEmailSender;

    public ForgetPasswordService(UserRepository userRepository, ForgetPasswordTokenRepository forgetPasswordTokenRepository, PasswordEncoder passwordEncoder, Forget_EmailSender forgetEmailSender) {
        this.userRepository = userRepository;
        this.forgetPasswordTokenRepository = forgetPasswordTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.forgetEmailSender = forgetEmailSender;
    }

    public void forgotPassword(ForgetPasswordRequest request) {

        User user=userRepository.findByEmail(request.getEmail()).orElseThrow(
                ()-> new RuntimeException("Account not exists"));

        forgetPasswordTokenRepository.deleteByUser(user);
        String token= UUID.randomUUID().toString();

        ForgetPasswordToken forgetPasswordToken=ForgetPasswordToken.builder()
                .token(token)
                .user(user)
                .expiryDate(Instant.now().plusSeconds(900))
                .build();

        forgetPasswordTokenRepository.save(forgetPasswordToken);
        String link = "http://localhost:5173/reset-password?token=" + token;
        forgetEmailSender.sendPasswordResetEmail(user.getEmail(), link);
    }

    public void resetPassword(ResetPasswordRequest request) {
    }
}
