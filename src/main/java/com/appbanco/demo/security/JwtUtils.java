package com.appbanco.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username) // "dueño" del jwt
                .setIssuedAt(now) //fecha emision
                .setExpiration(expiryDate) //cuándp expira
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) //firmamos token
                .compact(); // lo convierte a texto
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject(); // devuelve el dueño del token
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token); //verificamos si el token es legitimo
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Token inválido, expirado o mal formado
            return false;
        }
    }

    private Claims parseClaims(String token) { // lee el token
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // busca la firma
                .build()
                .parseClaimsJws(token) //verifica la firma
                .getBody(); //devuelve la informacion
    }
}
