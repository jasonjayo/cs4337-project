package com.example.api_gateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private static final String TEST_SECRET_KEY = "bf83d510d69b7a6db12aa6c4f2d51ad29945fcc70995041dc5affd897b9e33fc";

    @Test
    void testParseToken_ValidToken() {
        // Arrange
        String token = Jwts.builder()
                .setSubject("testuser")
                .signWith(Keys.hmacShaKeyFor(TEST_SECRET_KEY.getBytes()))
                .compact();

        // Act
        Claims claims = JwtUtils.parseToken(token);

        // Assert
        assertNotNull(claims);
        assertEquals("testuser", claims.getSubject());
    }

    @Test
    void testParseToken_InvalidToken() {
        // Arrange
        String invalidToken = "invalid.jwt.token";

        // Act & Assert
        assertThrows(Exception.class, () -> JwtUtils.parseToken(invalidToken));
    }
}
