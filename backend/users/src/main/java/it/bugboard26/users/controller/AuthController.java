package it.bugboard26.users.controller;

import java.util.UUID;

import it.bugboard26.users.dtos.LoginRequest;
import it.bugboard26.users.dtos.RegistrationRequest;
import it.bugboard26.users.dtos.UpdateUserRequest;
import it.bugboard26.users.dtos.UserResponse;
import it.bugboard26.users.entities.User;
import it.bugboard26.users.services.AuthService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest loginRequest) {
        User userLogged =  authService.login(loginRequest);
        return UserResponse.map(userLogged); 
    }
    
    @PostMapping("/register")
    public UUID register(@RequestBody RegistrationRequest registerRequest) {        
        User newUser = authService.register(registerRequest);
        return newUser.getUuid();
    }

    @PatchMapping
    public void updateUser(@RequestBody UpdateUserRequest updateRequest) {
        authService.updateUser(updateRequest);
    }

    
}
