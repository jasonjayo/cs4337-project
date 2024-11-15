package com.example.api_gateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private static final String SECRET_KEY = "bf83d510d69b7a6db12aa6c4f2d51ad29945fcc70995041dc5affd897b9e33fc";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    @Test
    void testParseToken_withValidToken() {
        // Create a valid token for testing
        String validToken = Jwts.builder()
                .setSubject("testUser")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) // 10 minutes expiration
                .signWith(key)
                .compact();

        // Parse the token
        Claims claims = JwtUtils.parseToken(validToken);

        // Assertions
        assertNotNull(claims);
        assertEquals("testUser", claims.getSubject());
    }

    @Test
    void testParseToken_withInvalidToken() {
        String invalidToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0VXNlciJ9.invalid_signature";

        assertThrows(io.jsonwebtoken.JwtException.class, () -> JwtUtils.parseToken(invalidToken));
    }
}
