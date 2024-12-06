package com.example.authservice.util;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.SignedJWT;

import java.util.Date;

/*
    Utility to create JWT
 */

public class JwtUtils {

    private static final String SECRET = "bf83d510d69b7a6db12aa6c4f2d51ad29945fcc70995041dc5affd897b9e33fc";
    // this secret will be used later in API gateway to validate the integrity of the token when included to
    // authenticate future requests

    /**
     * generates JWT token
     *
     * @param email      user's email (from Google)
     * @param first_name user's first name (from Google)
     * @param id         user's ID (our internal ID, not Google's user ID)
     * @return the generated token
     */
    public static String generateToken(String email, String first_name, int id) throws Exception {
        JWSSigner signer = new MACSigner(SECRET);


        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256),
                new com.nimbusds.jwt.JWTClaimsSet.Builder()
                        // add email, first name and user ID to token
                        // we'll then be able to use this info on frontend
                        .subject(email)
                        .claim("first_name", first_name)
                        .claim("id", id)
                        .issuer("findyourspark.ie")
                        .expirationTime(new Date(new Date().getTime() + 3600 * 1000)) // 1 hour expiry
                        .build()
        );

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }
}
