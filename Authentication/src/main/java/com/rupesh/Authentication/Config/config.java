package com.rupesh.Authentication.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class config {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception
    {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService)
//    {
//        return DaoAuthenticationProvider provider=new DaoAuthenticationProvider((userDetailsService);
//        provider.setPasswordEncoder(passwordEncoder());
//    }
}
