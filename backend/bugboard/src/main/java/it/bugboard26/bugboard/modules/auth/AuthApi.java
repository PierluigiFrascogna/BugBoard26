package it.bugboard26.bugboard.modules.auth;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.bugboard26.bugboard.modules.auth.dtos.LoginRequest;

import lombok.AllArgsConstructor;
    
@AllArgsConstructor
@CrossOrigin(origins = "https://app.bugboard26.it")
@RequestMapping("/auth")
@RestController
public class AuthApi {
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }
}
