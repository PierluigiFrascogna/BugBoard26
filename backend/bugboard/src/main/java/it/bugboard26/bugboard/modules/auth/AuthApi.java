package it.bugboard26.bugboard.modules.auth;

import java.util.UUID;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.bugboard26.bugboard.auth.Jwt;
import it.bugboard26.bugboard.microservices.users.UsersMicroservice;
import it.bugboard26.bugboard.modules.auth.dtos.JwtResponse;
import it.bugboard26.bugboard.modules.auth.dtos.LoginRequest;
import it.bugboard26.bugboard.modules.auth.dtos.UpdateUserRequest;
import lombok.AllArgsConstructor;
    
@AllArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthApi {
    private Jwt jwtUser;
    private AuthService authService;
    private UsersMicroservice usersMicroService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }

    @PatchMapping
    public void updateUser(@RequestBody UpdateUserRequest updateRequest) {
        UUID uuidRequester = jwtUser.getUserUuid();  
        updateRequest.setUuidRequester(uuidRequester);
        usersMicroService.updateUser(updateRequest);
    }
}
