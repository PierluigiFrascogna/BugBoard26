package it.bugboard26.bugboard.modules.auth;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.bugboard26.bugboard.modules.auth.dtos.JwtResponse;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;

@Service
public class JwtService {
    //Attribute
    private SecretKey secretKey;

    //Constructor
    public JwtService(@Value("${jwt.key}") String jwtSecretKey) {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    //Methods
    public Jws<Claims> parseToken(String rawToken) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(rawToken);
    }

     public JwtResponse generateToken(UserResponse user) {
        String jwt = Jwts.builder()
            .subject(user.getUuid().toString())
            .claim("name", user.getName())
            .claim("surname", user.getSurname())
            .claim("role", user.getRole().toString())
            .issuedAt(new Date())
            //.expiration(new Date(System.currentTimeMillis() + 3600000)) // 1 ora
            .signWith(secretKey)
            .compact();

        return new JwtResponse(jwt);
    }

}
