package it.bugboard26.bugboard.modules.auth;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.modules.auth.dtos.LoginRequest;
import it.bugboard26.bugboard.modules.auth.dtos.RegistrationRequest;
import it.bugboard26.bugboard.users_micro_service.UsersMicroService;

import lombok.AllArgsConstructor;
    
@AllArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthApi {
    private AuthService authService;
    private UsersMicroService usersMicroService;
    
    @PostMapping("/register")
    public User register(@RequestBody RegistrationRequest registrationRequest) {
        if (!authService.isAdmin()) 
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can register new users");
    
        UUID uuid_newUser = usersMicroService.registerUser(registrationRequest);
        User newUser = new User(uuid_newUser, registrationRequest.getRole());
        return authService.registerUser(newUser);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }
}
