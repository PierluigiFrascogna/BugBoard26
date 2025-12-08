package it.bugboard26.users.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.bugboard26.users.entities.User;
import it.bugboard26.users.exceptions.AuthenticationException;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.mindrot.jbcrypt.BCrypt;

@Service
public class AuthService {

    //Attributes 
    private UserService userService;
    private SecretKey secretKey;


    //Constructor
    public AuthService(UserService userService, @Value("${jwt.key}") String jwtSecret){
        this.userService = userService;
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }


    //Methods
    public String login(String email, String rawPassword) {
        User user = verifyUser(email, rawPassword);
        return generateJwt(user);
    }

    private User verifyUser(String email, String rawPassword) {
        User user = userService.findByEmail(email).orElseThrow(() -> new AuthenticationException("Invalid credentials"));

        if(!BCrypt.checkpw(rawPassword, user.getPasswordHash())) 
            throw new AuthenticationException("Invalid credentials");

        return user;
    }

    private String generateJwt(User user) {
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
}
