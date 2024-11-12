package com.example.authservice.config;

import com.example.authservice.util.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

            String email = token.getPrincipal().getAttributes().get("email").toString();

            try {
                String jwtToken = JwtUtils.generateToken(email);

                // redirect to Vue frontend with JWT token
//                response.sendRedirect("http://localhost:8084/home?token=" + jwtToken);
                response.sendRedirect("http://localhost:5173/oauth2/redirect?token=" + jwtToken);
            } catch (Exception e) {
                throw new ServletException("Error generating JWT token", e);
            }
        } else {
            throw new ServletException("Authentication object is not of type OAuth2AuthenticationToken");
        }
    }
}
