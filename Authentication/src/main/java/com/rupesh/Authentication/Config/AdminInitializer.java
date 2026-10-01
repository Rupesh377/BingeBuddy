package com.rupesh.Authentication.Config;

import com.rupesh.Authentication.Entity.User;
import com.rupesh.Authentication.Enum.AuthProvider;
import com.rupesh.Authentication.Enum.Role;
import com.rupesh.Authentication.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (userRepository.count() == 0) {
                userRepository.save(User.builder().
                        username("admin")
                        .email("rupesh241B215@juetguna.in")
                        .password(encoder.encode("Admin123"))
                        .role(Role.ADMIN)
                        .provider(AuthProvider.LOCAL)
                        .build());
            }
        };
    }
}
