package it.bugboard26.users.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.bugboard26.users.dtos.RegisterRequest;
import it.bugboard26.users.entities.User;
import it.bugboard26.users.exceptions.AuthenticationException;
import it.bugboard26.users.exceptions.InvalidEmailException;
import it.bugboard26.users.repositories.UserRepository;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.mindrot.jbcrypt.BCrypt;

@Service
public class AuthService {

    //Attributes 
    private UserRepository userRepository;
    private SecretKey secretKey;


    //Constructor
    public AuthService(UserRepository userRepository, @Value("${jwt.key}") String jwtSecret){
        this.userRepository = userRepository;
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }


    //Methods
    public String verifyAndGenerateToken(String email, String rawPassword) {
        User user = verifyUser(email, rawPassword);
        return generateJwt(user);
    }

    private User verifyUser(String email, String rawPassword) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AuthenticationException("Invalid credentials"));

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

    public User register(RegisterRequest registerDTO) {
        if(userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new InvalidEmailException("Email already in use");
        }

        String hashedPassword = BCrypt.hashpw(registerDTO.getPassword(), BCrypt.gensalt());

        User newUser = new User(
            registerDTO.getName(),
            registerDTO.getSurname(),
            registerDTO.getEmail(),
            hashedPassword,
            false 
        );

        userRepository.save(newUser);
        return newUser;
    }
}
