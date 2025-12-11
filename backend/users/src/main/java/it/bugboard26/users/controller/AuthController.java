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
    public ResponseEntity<UUID> register(
    @RequestHeader("Authorization") String authHeader, 
    @RequestBody RegistrationRequest registerDTO) {

        if(authHeader == null || !StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Access denied");

        String token = authHeader.substring(7); // Rimuove "Bearer"
        boolean isAdmin = jwtService.validateAdmin(token);

        if(!isAdmin) 
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        
        User newUser = authService.register(registerDTO);
        return ResponseEntity.ok(newUser.getUuid());
    }

    
}
