package it.bugboard26.bugboard.modules.auth;

import org.springframework.stereotype.Service;

import it.bugboard26.bugboard.enums.Role;
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
        Role role = userService.getByUuid(userResponse.getUuid()).getRole();
        userResponse.setRole(role);
        return jwtService.generateToken(userResponse);
    }

}