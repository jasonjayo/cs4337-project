package com.example.shared.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;

public class AuthUtils {
    public static boolean checkToken(String token, Long id) {
        try {
            String decodedToken = new String(Base64.getDecoder().decode(token.split("\\.")[1]));
            int tokenUserId;
            tokenUserId = new ObjectMapper().readTree(decodedToken).get("id").asInt();
            return tokenUserId == id;
        } catch (JsonProcessingException e) {
            return false;
        }
    }
}
