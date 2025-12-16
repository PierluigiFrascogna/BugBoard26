package it.bugboard26.bugboard.modules.auth;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.bugboard26.bugboard.enums.Role;

@Service
public class JwtService {
    //Attribute
    private SecretKey secretKey;

    //Constructor
    public JwtService(@Value("${jwt.key}") String jwtSecretKey) {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    //Methods for extracting info from OUR jwt token
    public UUID getUUID(Jws<Claims> token) {
        return UUID.fromString(token.getPayload().getSubject());
    }

    public String getName(Jws<Claims> token) {
        return token.getPayload().get("name", String.class);
    }

    public String getSurname(Jws<Claims> token) {
        return token.getPayload().get("surname", String.class);
    } 

    public Role getRole(Jws<Claims> token) {
        return token.getPayload().get("role", Role.class);
    }


    // Methods for generic jwt token
    public Claims getPayload(Jws<Claims> token) {
        return token.getPayload();
    }

    public Jws<Claims> parseToken(String rawToken) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(rawToken);
    }

}
