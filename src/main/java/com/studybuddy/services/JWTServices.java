
package com.studybuddy.services;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTServices {

    static String TOP_SECRET = System.getenv("STUDY_BUDDY_JWT_SECRET");

    static Algorithm algo = Algorithm.HMAC256(TOP_SECRET);

    public static String generateJWT(int OTP) {

        Date currentDateTime = new Date();
        long currentTime = currentDateTime.getTime();
        long expireDuration = 2 * 60 * 1000;
        long expireTime = currentTime + expireDuration;
        Date expireDateTime = new Date(expireTime);

        String token = JWT.create()
            .withExpiresAt(expireDateTime)
            .withIssuer("Study-Buddy")
            .withIssuedAt(currentDateTime)
            .withClaim("OTP", OTP)
            .sign(algo);

        return token;
    }

    public static String generateResetToken(String email) {

        Date currentDateTime = new Date();
        long currentTime = currentDateTime.getTime();
        long expireDuration = 10 * 60 * 1000;
        long expireTime = currentTime + expireDuration;
        Date expireDateTime = new Date(expireTime);

        String token = JWT.create()
            .withExpiresAt(expireDateTime)
            .withIssuer("Study-Buddy")
            .withIssuedAt(currentDateTime)
            .withClaim("email", email)
            .sign(algo);

        return token;
    }

    public static String verifyJWT(String token) {

        try {
            DecodedJWT decryptedData = JWT.require(algo)
                    .withIssuer("Study-Buddy")
                    .build().verify(token);

            return String.valueOf(decryptedData.getClaim("OTP").asInt());

        } catch (Exception e) {
            return null;
        }
    }

    public static String verifyResetLink(String token) {

        try {
            DecodedJWT decryptedData = JWT.require(algo)
                    .withIssuer("Study-Buddy")
                    .build().verify(token);

            return decryptedData.getClaim("email").asString();

        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }
}
