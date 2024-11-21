package com.example.authservice.config;

import com.example.shared.feign.PlayerClient;
import com.example.authservice.util.JwtUtils;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

import com.example.shared.dto.PlayerDTO;


@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private PlayerClient playerClient;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

            String email = token.getPrincipal().getAttributes().get("email").toString();
            String first_name = token.getPrincipal().getAttributes().get("given_name").toString();
            String google_id = token.getPrincipal().getAttributes().get("sub").toString();
            String profile_pic = token.getPrincipal().getAttributes().get("picture").toString();

            int player_id;

            try {
                PlayerDTO existing_player = playerClient.getPlayerByGoogleId(google_id);
                player_id = existing_player.getPlayerId();
            } catch (FeignException.NotFound e) {
                // Create PlayerDTO and send it to the player service
                PlayerDTO playerDTO = new PlayerDTO();
                playerDTO.setGoogleId(google_id);
                playerDTO.setPlayerName(first_name);

                // Call the player service to create a new player entry
                PlayerDTO player = playerClient.createPlayer(playerDTO);
                player_id = player.getPlayerId();
            }

            try {
                String jwtToken = JwtUtils.generateToken(email, first_name, player_id);

                // Redirect to the frontend with the correct URL
                // Using the frontend container name and port in the Docker setup
                String frontendUrl = "http://127.0.0.1:5173/oauth2/redirect";

                // Construct the redirection URL with JWT and profile picture
                String redirectUrl = frontendUrl + "?token=" + jwtToken + "&profile_pic=" + profile_pic;

                response.sendRedirect(redirectUrl);
            } catch (Exception e) {
                throw new ServletException("Error generating JWT token", e);
            }
        } else {
            throw new ServletException("Authentication object is not of type OAuth2AuthenticationToken");
        }
    }
}
