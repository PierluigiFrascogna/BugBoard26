package it.bugboard26.users.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import it.bugboard26.users.dtos.LoginRequest;
import it.bugboard26.users.services.AuthService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@AllArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200") // Per far comunicare Angular in locale
public class AuthController {

    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginDTO) {
        String jwt = authService.login(loginDTO.getEmail(), loginDTO.getPassword());
        return ResponseEntity.ok(jwt);   
    }
    

    
}
