package com.rupesh.Authentication.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Forget_EmailSender {

    private final JavaMailSender mailSender;

    public Forget_EmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String email, String link) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset your password");
        message.setText("""
            Click the link below to reset your password:
            %s
            This link expires in 15 minutes.
            """.formatted(link));
        mailSender.send(message);
    }
}
