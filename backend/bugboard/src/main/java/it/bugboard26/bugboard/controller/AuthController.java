package it.bugboard26.bugboard.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.bugboard26.bugboard.dtos.RegistrationRequest;
import it.bugboard26.bugboard.services.AuthService;
import lombok.AllArgsConstructor;
    
@AllArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    AuthService authService;

    @PostMapping("/register")
    public void register(@RequestHeader("Authorization") String authHeader, @RequestBody RegistrationRequest registerRequest) {
        authService.registerUserViaMicroservice(authHeader, registerRequest);
    }
}
