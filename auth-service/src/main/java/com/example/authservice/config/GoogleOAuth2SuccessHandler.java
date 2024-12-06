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


import com.example.shared.dto.PlayerDTO;

/*
    Deal with successful authentication through OAuth
 */

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private PlayerClient playerClient; // feign client for interservice communication

    /**
     * exchanges token from Google for JWT token
     *
     * @param request        the current HTTP request
     * @param response       the response we'll serve
     * @param authentication representing user's auth token from Google
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException {
        if (authentication instanceof OAuth2AuthenticationToken token) {

            // get info from Google token
            String email = token.getPrincipal().getAttributes().get("email").toString();
            String first_name = token.getPrincipal().getAttributes().get("given_name").toString();
            String google_id = token.getPrincipal().getAttributes().get("sub").toString();
            String profile_pic = token.getPrincipal().getAttributes().get("picture").toString();

            int player_id;

            try {
                // if existing player, get ID
                PlayerDTO existing_player = playerClient.getPlayerByGoogleId(google_id);
                player_id = existing_player.getPlayerId();
            } catch (FeignException.NotFound e) {
                // if new player, create record in our DB
                PlayerDTO playerDTO = new PlayerDTO();
                playerDTO.setGoogleId(google_id);
                playerDTO.setPlayerName(first_name);

                // call player service to create a new player entry (feign client, interservice communication)
                PlayerDTO player = playerClient.createPlayer(playerDTO);
                player_id = player.getPlayerId();
            }

            try {
                // now using data from the Google token, we generate our own JWT token to give back to user
                // this will be used for future requests
                // we'll store the email, first name and player ID in the contents of this JWT token
                String jwtToken = JwtUtils.generateToken(email, first_name, player_id);

                // next redirect user back to our frontend
                String frontendUrl = "http://127.0.0.1:5173/oauth2/redirect";

                // pass the token and profile pic URL in the URL params so they can be processed by frontend
                String redirectUrl = frontendUrl + "?token=" + jwtToken + "&profile_pic=" + profile_pic;

                // and finally execute the redirect
                response.sendRedirect(redirectUrl);
            } catch (Exception e) {
                throw new ServletException("Error generating JWT token", e);
            }
        } else {
            // unexpected object, not an OAuth token so cannot proceed
            throw new ServletException("Authentication object is not of type OAuth2AuthenticationToken");
        }
    }
}
