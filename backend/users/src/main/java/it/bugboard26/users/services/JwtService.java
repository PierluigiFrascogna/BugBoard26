package it.bugboard26.users.services;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.bugboard26.users.entities.User;

@Service
public class JwtService {

    private SecretKey secretKey;

    public JwtService(@Value("${jwt.key}") String jwtSecretKey) {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }


    public String generateJwt(User user) {
        String jwt = Jwts.builder()
            .subject(user.getUuid().toString())
            .claim("name", user.getName())
            .claim("surname", user.getSurname())
            .claim("admin", user.isAdmin())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 300000)) // 5 minuti
            .signWith(secretKey)
            .compact();

        return jwt;
    }

    public boolean validateAdmin(String token) {
        try {
            return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("admin", Boolean.class);
        } catch (Exception e) {
            return false;
        }
    }

}
