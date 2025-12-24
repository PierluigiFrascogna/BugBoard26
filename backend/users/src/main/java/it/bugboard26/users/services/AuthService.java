package it.bugboard26.users.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import it.bugboard26.users.dtos.LoginRequest;
import it.bugboard26.users.dtos.RegistrationRequest;
import it.bugboard26.users.entities.User;
import lombok.AllArgsConstructor;

import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

@AllArgsConstructor
@Service
public class AuthService {
    private UserService userService;
    private JwtService jwtService;

    public String verifyAndGenerateToken(LoginRequest frontendRequest) {
        User user = userService.verifyUser(frontendRequest.getEmail(), frontendRequest.getPassword());
        return jwtService.generateJwt(user);
    }

    public User register(RegistrationRequest frontendRequest) {
        if(userService.existsByEmail(frontendRequest.getEmail())) 
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");

        String hashedPassword = BCrypt.hashpw(frontendRequest.getPassword(), BCrypt.gensalt());
        User newUser = new User(
            frontendRequest.getName(),
            frontendRequest.getSurname(),
            frontendRequest.getEmail(),
            hashedPassword,
            frontendRequest.isAdmin()
        );
        userService.save(newUser);
        return newUser;
    }
    
    public User getAuthenticatedUser(@RequestHeader("Authorization") String authHeader) {
        String rawToken = authHeader.substring(7); // Rimuove "Bearer " dall'inizio
        Jws<Claims> parsedToken = jwtService.parseToken(rawToken);
        UUID user_uuid = UUID.fromString(parsedToken.getPayload().getSubject());
        return userService.findByUuid(user_uuid);
    }

}
