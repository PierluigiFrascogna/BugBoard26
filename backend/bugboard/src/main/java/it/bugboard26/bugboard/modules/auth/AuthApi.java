package it.bugboard26.bugboard.modules.auth;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.Role;
import it.bugboard26.bugboard.modules.auth.dtos.RegistrationRequest;
import it.bugboard26.bugboard.modules.projects.HeaderRequestService;
import it.bugboard26.bugboard.modules.users.UserService;
import lombok.AllArgsConstructor;
    
@AllArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthApi {
    private HeaderRequestService headerRequest;
    private JwtService jwtService;
    private UserService userService;
    private AuthService authService;
    
    @PostMapping("/register")
    public void register(@RequestBody RegistrationRequest registrationRequest) {
        if (!headerRequest.hasAuthorization()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");

        Jws<Claims> token = jwtService.parseToken(headerRequest.extractToken());
        UUID uuid_userLogged = jwtService.getUUID(token);
        User userLogged = userService.getByUuid(uuid_userLogged);

        if (userLogged.getRole() != Role.ADMIN) 
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only ADMIN users can register new users");

        UUID uuid_newUser = authService.registerUserViaMicroservice(registrationRequest);
        User newUser = new User(uuid_newUser, registrationRequest.getRole());
        authService.registerUser(newUser);
    }
}
