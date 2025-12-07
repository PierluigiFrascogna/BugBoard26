package it.bugboard26.users.services;

import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SecretJwkBuilder;
import it.bugboard26.users.entities.User;
import it.bugboard26.users.exceptions.AuthenticationException;

import java.util.Date;

import org.mindrot.jbcrypt.BCrypt;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthService {

    private UserService userService;

    public String login(String email, String rawPassword) {
        User user = verifyUser(email, rawPassword);
        return generateJwt(user);
    }

    private User verifyUser(String email, String rawPassword) {
        User user = userService.findByEmail(email);

        if(!BCrypt.checkpw(rawPassword, user.getPasswordHash())) 
            throw new AuthenticationException("Password errata");

        return user;
    }

    private String generateJwt(User user) {

        String jwt = Jwts.builder()
            .header()
                .type("JWT")
            
            .and()
                .subject(user.getUuid().toString())
                .claim("name", user.getName())
                .claim("surname", user.getSurname())
                .claim("admin", user.isAdmin())
                .issuedAt(new Date())
                .signWith(null)

            .compact();

        return jwt;
    }
}
