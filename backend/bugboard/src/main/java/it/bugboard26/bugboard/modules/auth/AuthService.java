package it.bugboard26.bugboard.modules.auth;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.modules.projects.HeaderRequestService;
import it.bugboard26.bugboard.modules.users.UserService;

import org.springframework.http.HttpStatus;

import java.util.UUID;


@Service
public class AuthService {
    //Attributes
    private JwtService jwtService;
    private UserService userService;
    private HeaderRequestService headerRequest;
    private AuthRepository authRepository;

    //Constructor
    public AuthService(JwtService jwtService, UserService userService, HeaderRequestService headerRequest, AuthRepository authRepository) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.headerRequest = headerRequest;
        this.authRepository = authRepository;
    }

    //Methods
    public void registerUser(User user) {
        authRepository.save(user);
    }

    public User getAuthenticatedUser() {
        if (!headerRequest.hasAuthorization()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");

        Jws<Claims> token = jwtService.parseToken(headerRequest.extractToken());
        UUID uuid_user = UUID.fromString(token.getPayload().getSubject());
        User user = userService.getByUuid(uuid_user);
        return user;
    }
}