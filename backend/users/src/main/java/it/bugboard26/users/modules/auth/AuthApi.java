package it.bugboard26.users.modules.auth;

import it.bugboard26.users.entities.User;
import it.bugboard26.users.modules.auth.dtos.LoginRequest;
import it.bugboard26.users.modules.auth.dtos.UpdateUserRequest;
import it.bugboard26.users.modules.auth.dtos.UserResponse;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthApi {
    private AuthService authService;

    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest loginRequest) {
        User userLogged =  authService.login(loginRequest);
        return UserResponse.map(userLogged); 
    }

    @PatchMapping
    public void updateUser(@RequestBody UpdateUserRequest updateRequest) {
        authService.updateUser(updateRequest);
    }

    
}
