package com.bokyoung.preorderservice.util;

import io.jsonwebtoken.Jwts;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtils {

    public static String generateToken(String email, String key, long expiredTimeMs) {

        return Jwts.builder()
                .claim("email", email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() * expiredTimeMs))
                .signWith(getKey(key))
                .compact();
    }

    private static Key getKey(String key) {
        return new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }
}
