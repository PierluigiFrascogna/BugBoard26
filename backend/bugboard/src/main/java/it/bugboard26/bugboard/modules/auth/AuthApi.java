package it.bugboard26.bugboard.modules.auth;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.modules.auth.dtos.RegistrationRequest;
import lombok.AllArgsConstructor;
    
@AllArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthApi {

    AuthService authService;

    @PostMapping("/register")
    public void register(@RequestHeader("Authorization") String authHeader, @RequestBody RegistrationRequest registerRequest) {
        UUID uuid = authService.registerUserViaMicroservice(authHeader, registerRequest);
        User user = new User(uuid, registerRequest.getRole());
        authService.registerUser(user);
    }
}
