package com.example.authservice.util;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.SignedJWT;

import java.util.Date;

public class JwtUtils {

    private static final String SECRET = "bf83d510d69b7a6db12aa6c4f2d51ad29945fcc70995041dc5affd897b9e33fc";

    public static String generateToken(String email) throws Exception {
        JWSSigner signer = new MACSigner(SECRET);

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256),
                new com.nimbusds.jwt.JWTClaimsSet.Builder()
                        .subject(email)
                        .issuer("example.com")
                        .expirationTime(new Date(new Date().getTime() + 3600 * 1000)) // 1 hour expiry
                        .build()
        );

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }
}
