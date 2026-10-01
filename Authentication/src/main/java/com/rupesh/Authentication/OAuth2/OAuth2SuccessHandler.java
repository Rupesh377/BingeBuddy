package com.rupesh.Authentication.OAuth2;

import com.rupesh.Authentication.DTOs.AuthResponseDTO;
import com.rupesh.Authentication.Enum.AuthProvider;
import com.rupesh.Authentication.Repository.UserRepository;
import com.rupesh.Authentication.Service.JwtService;
import com.rupesh.Authentication.Service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final OAuth2Service oAuth2Service;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        OAuth2User oAuthUser = (OAuth2User) authentication.getPrincipal();
        String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();

        String email = oAuthUser.getAttribute("email");
        String name = oAuthUser.getAttribute("name");

        AuthProvider provider = registrationId.equals("google") ? AuthProvider.GOOGLE : AuthProvider.GITHUB;
        AuthResponseDTO authResponseDTO;

        try {
            authResponseDTO = oAuth2Service.login(email, name, provider);
        } catch (RuntimeException ex) {
            String errorMessage = URLEncoder.encode(ex.getMessage(), StandardCharsets.UTF_8);
            String redirectUrl = "http://localhost:3000/login?error=" + errorMessage;

            getRedirectStrategy().sendRedirect(request, response, redirectUrl);
            return;
        }
        String redirectUrl = "http://localhost:3000/oauth/success"
                + "?accessToken=" + authResponseDTO.getAccessToken()
                + "&refreshToken=" + authResponseDTO.getRefreshToken();

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
