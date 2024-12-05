package com.example.api_gateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

/*
    Utility to parse JWT auth token
 */

public class JwtUtils {

    private static final String SECRET_KEY = "bf83d510d69b7a6db12aa6c4f2d51ad29945fcc70995041dc5affd897b9e33fc";

    public static Claims parseToken(String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody(); // return contents of token
    }
}
