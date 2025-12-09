package it.bugboard26.users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import it.bugboard26.users.dtos.LoginRequest;
import it.bugboard26.users.dtos.RegisterRequest;
import it.bugboard26.users.dtos.UserResponse;
import it.bugboard26.users.entities.User;
import it.bugboard26.users.exceptions.ForbiddenException;
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
    public ResponseEntity<UserResponse> register(
    @RequestHeader("Authorization") String authHeader, 
    @RequestBody RegisterRequest registerDTO) {

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ForbiddenException("Access denied");
        }

        String token = authHeader.substring(7); // Rimuovi "Bearer"
        boolean isAdmin = jwtService.validateAdmin(token);

        if(!isAdmin) 
            throw new ForbiddenException("Access denied");
        
        User newUser = authService.register(registerDTO);
        UserResponse userResponse = new UserResponse(newUser);
        return ResponseEntity.ok(userResponse);
    }

    
}
