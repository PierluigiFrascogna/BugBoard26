package it.bugboard26.users.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.bugboard26.users.dtos.LoginRequest;
import it.bugboard26.users.dtos.RegistrationRequest;
import it.bugboard26.users.entities.User;
import it.bugboard26.users.services.AuthService;
import it.bugboard26.users.services.JwtService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;


@AllArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200") // Per far comunicare Angular in locale
public class AuthController {
    
    private AuthService authService;
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginDTO) {
        String jwt = authService.verifyAndGenerateToken(loginDTO);
        return ResponseEntity.ok(jwt);   
    }
    
    @PostMapping("/register")
    public UUID register(@RequestBody RegistrationRequest registerDTO) {        
        User newUser = authService.register(registerDTO);
        return newUser.getUuid();
    }

    
}
