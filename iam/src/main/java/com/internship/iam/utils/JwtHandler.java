package com.internship.iam.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;


/**
 * @author Mentor H
 */
public class JwtHandler {
    private static final String TOKEN_SECRET_KEY = "ThisIsAFakeSecretItShouldBeStoredInAConfigurationFileInsteadOfAConstantYouMustMoveItToConfigFile";
    private static final Long TOKEN_TTL = 1000 * 60 * 60 * 10L; // 10 hours


    /**
     * @author Mentor H
     */
    public static String generateToken(String userId) {
        Date now = new Date(System.currentTimeMillis());
        Key secretKey = Keys.hmacShaKeyFor(TOKEN_SECRET_KEY.getBytes());
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TOKEN_TTL))
                .signWith(secretKey)
                .compact();
    }

    /**
     * @author Mentor H
     */
    public static boolean isValid(String jwt) {
        try {
            Key secretKey = Keys.hmacShaKeyFor(TOKEN_SECRET_KEY.getBytes());
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build();
            parser.parse(jwt);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * @author Mentor H
     */
    public static String getUserId(String jwtStr) {
        Key secretKey = Keys.hmacShaKeyFor(TOKEN_SECRET_KEY.getBytes());
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
        return parser.parseClaimsJws(jwtStr)
                .getBody()
                .getSubject();
    }

}
