package it.bugboard26.bugboard.modules.auth;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.modules.auth.dtos.LoginRequest;
import it.bugboard26.bugboard.modules.users.UserRepository;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import it.bugboard26.bugboard.users_micro_service.UsersMicroService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Service
public class AuthService {
    //Attributes
    private JwtService jwtService;
    private UsersMicroService usersMicroService;
    private UserRepository userRepository;

    //Methods
    public String loginUser(LoginRequest loginRequest) {
        UserResponse userResponse = usersMicroService.loginUser(loginRequest);
        User user = userRepository.findById(userResponse.getUuid()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        userResponse.setRole(user.getRole());
        return jwtService.generateToken(userResponse);
    }

}