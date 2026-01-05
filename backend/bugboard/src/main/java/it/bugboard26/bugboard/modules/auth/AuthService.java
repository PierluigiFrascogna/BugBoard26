package it.bugboard26.bugboard.modules.auth;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.Role;
import it.bugboard26.bugboard.modules.auth.dtos.LoginRequest;
import it.bugboard26.bugboard.modules.projects.HeaderRequestService;
import it.bugboard26.bugboard.modules.users.UserRepository;
import it.bugboard26.bugboard.users_micro_service.UserResponse;
import it.bugboard26.bugboard.users_micro_service.UsersMicroService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Service
public class AuthService {
    //Attributes
    private HeaderRequestService headerRequest;
    private JwtService jwtService;
    private UsersMicroService usersMicroService;
    private UserRepository userRepository;

    //Methods
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public String loginUser(LoginRequest loginRequest) {
        UserResponse userResponse = usersMicroService.loginUser(loginRequest);
        User user = userRepository.findById(userResponse.getUuid()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        userResponse.setRole(user.getRole());
        return jwtService.generateToken(userResponse);
    }

    public boolean isAdmin() {
        if (!headerRequest.hasAuthorization()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");

        Jws<Claims> token = jwtService.parseToken(headerRequest.extractToken());
        return token.getPayload().get("role", String.class) == Role.ADMIN.toString();
    }

}