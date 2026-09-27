package com.rupesh.Authentication.Security;

import com.rupesh.Authentication.Entity.User;
import com.rupesh.Authentication.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user=userRepository.findByUsername(username).orElseThrow(
                ()-> new RuntimeException("User not found"));
        return new CustomUserDetails(user);
    }
}
