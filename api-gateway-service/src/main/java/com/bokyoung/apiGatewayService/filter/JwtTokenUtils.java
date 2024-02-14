package com.bokyoung.apiGatewayService.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtils {

    public static String getEmail(String token, String key) {
        return extractClaims(token, key).get("email", String.class);
    }

    public static String getUserId(String token, String key) {
        return extractClaims(token, key).getSubject();
    }

    public static boolean isExpired(String token, String key) {
        Date expiredDate = extractClaims(token, key).getExpiration();
        return expiredDate.before(new Date());
    }

    private static Claims extractClaims(String token, String key) {
        return Jwts.parser().verifyWith((SecretKey) getKey(key))
                .build().parseSignedClaims(token).getPayload();
    }

    private static Key getKey(String key) {
        return new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }
}