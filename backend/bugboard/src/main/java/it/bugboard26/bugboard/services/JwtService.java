package it.bugboard26.bugboard.services;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {
    //Attribute
    private SecretKey secretKey;

    //Constructor
    public JwtService(@Value("${jwt.key}") String jwtSecretKey) {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    //Methods
    public UUID getUUID(String jwtToken) {
        Claims claims = this.parsePayload(jwtToken);
        String userUuidString = claims.getSubject();
        return UUID.fromString(userUuidString);
    }

    private Claims parsePayload(String jwtToken) {
        return Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(jwtToken)
        .getPayload();
    }

  

}
