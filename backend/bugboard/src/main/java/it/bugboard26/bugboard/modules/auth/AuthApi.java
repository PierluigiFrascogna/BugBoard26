package it.bugboard26.bugboard.modules.auth;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import it.bugboard26.bugboard.microservices.users.UsersMicroservice;
import it.bugboard26.bugboard.modules.auth.dtos.JwtResponse;
import it.bugboard26.bugboard.modules.auth.dtos.LoginRequest;
import it.bugboard26.bugboard.modules.auth.dtos.UpdateUserRequest;
import it.bugboard26.bugboard.modules.projects.HeaderRequestService;
import lombok.AllArgsConstructor;
    
@AllArgsConstructor
@CrossOrigin(origins = "https://app.bugboard26.it")
@RequestMapping("/auth")
@RestController
public class AuthApi {
    private HeaderRequestService headerRequest;
    private JwtService jwtService; 
    private AuthService authService;
    private UsersMicroservice usersMicroService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }

    @PatchMapping
    public void updateUser(@RequestBody UpdateUserRequest updateRequest) {
        if (!headerRequest.hasAuthorization()) 
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        
        Jws<Claims> token = jwtService.parseToken(headerRequest.extractToken());
        UUID uuidRequester = UUID.fromString(token.getPayload().getSubject());  
        updateRequest.setUuidRequester(uuidRequester);
        usersMicroService.updateUser(updateRequest);
    }
}
