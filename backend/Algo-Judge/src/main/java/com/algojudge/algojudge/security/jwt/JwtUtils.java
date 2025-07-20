package com.algojudge.algojudge.security.jwt;

import com.algojudge.algojudge.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${onlinejudge.app.jwtSecret}")
    private String jwtSecret;

    @Value("${onlinejudge.app.jwtExpiration}")
    private int jwtExpiration;

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername())) // Subject is the user's email
                .setIssuedAt(new Date()) // Token creation date
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration)) // Token expiration date
                .signWith(key(), SignatureAlgorithm.HS256) // Sign the token with our secret key and algorithm
                .compact();
    }

    public String getUserNameFromJwtToken(String token){
        SecretKey secretKey = (SecretKey) key();
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken){
        try {
            SecretKey secretKey = (SecretKey) key(); // Get your secret key
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        } catch (io.jsonwebtoken.security.SignatureException e) { // Added for signature validation failures
            logger.error("Invalid JWT signature: {}", e.getMessage());
        }
        return false;
    }
}
