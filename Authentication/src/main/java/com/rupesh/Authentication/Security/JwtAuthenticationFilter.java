package com.rupesh.Authentication.Security;

import com.rupesh.Authentication.Entity.User;
import com.rupesh.Authentication.Repository.UserRepository;
import com.rupesh.Authentication.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header=request.getHeader("Authorization");
        if(header == null || ! header.startsWith("Bearer "))
        {
            filterChain.doFilter(request , response);
            return;
        }
        String token=header.substring(7);
        try
        {
            UUID id=jwtService.extractUserId(token);
            User user= userRepository.findById(id).orElse(null);

            if (user !=null && SecurityContextHolder.getContext().getAuthentication() == null
                    && jwtService.isTokenValid(token, user)) {

                CustomUserDetails userDetails = new CustomUserDetails(user);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ignored) {
        }
        filterChain.doFilter(request , response);
    }
}
