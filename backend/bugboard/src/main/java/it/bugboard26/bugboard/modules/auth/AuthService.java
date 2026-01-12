package it.bugboard26.bugboard.modules.auth;

import org.springframework.stereotype.Service;

import it.bugboard26.bugboard.auth.JwtService;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.microservices.users.UsersMicroservice;
import it.bugboard26.bugboard.modules.auth.dtos.JwtResponse;
import it.bugboard26.bugboard.modules.auth.dtos.LoginRequest;
import it.bugboard26.bugboard.modules.users.UserService;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class AuthService {
    //Attributes
    private JwtService jwtService;
    private UsersMicroservice usersMicroService;
    private UserService userService;

    //Methods
    public JwtResponse loginUser(LoginRequest loginRequest) {
        UserResponse userResponse = usersMicroService.loginUser(loginRequest);
        User user = userService.getByUuid(userResponse.getUuid()); 
        userResponse.setRole(user.getRole());
        return jwtService.generateToken(userResponse);
    }

}