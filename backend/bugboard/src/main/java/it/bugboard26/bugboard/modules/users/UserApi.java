package it.bugboard26.bugboard.modules.users;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.Role;
import it.bugboard26.bugboard.modules.auth.JwtService;
import it.bugboard26.bugboard.modules.projects.HeaderRequestService;
import it.bugboard26.bugboard.modules.users.dtos.RegistrationRequest;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import it.bugboard26.bugboard.users_micro_service.UsersMicroService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@CrossOrigin(origins = "https://app.bugboard26.it")
@RestController
public class UserApi {
    private HeaderRequestService headerRequest;
    private JwtService jwtService;
    private UserService userService;
    private UsersMicroService usersMicroService;

    @PostMapping("/register")
    public User register(@RequestBody RegistrationRequest registrationRequest) {
        if (!headerRequest.hasAuthorization()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");
        
        Jws<Claims> token = jwtService.parseToken(headerRequest.extractToken());
        Role role = Role.valueOf(token.getPayload().get("role", String.class));
        if (role != Role.ADMIN) 
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can register new users");
        
        UUID uuid_newUser = usersMicroService.registerUser(registrationRequest);
        User newUser = new User(uuid_newUser, registrationRequest.getRole());
        return userService.registerUser(newUser);
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        if (!headerRequest.hasAuthorization()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");

        Jws<Claims> token = jwtService.parseToken(headerRequest.extractToken());
        Role role = Role.valueOf(token.getPayload().get("role", String.class));
        if (role != Role.ADMIN) 
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can get all users");
       
        return userService.getAllUsers();
    }

    @DeleteMapping("/users/{uuid_user}")
    public void deleteUser(@PathVariable UUID uuid_user) {
        if (!headerRequest.hasAuthorization()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");

        Jws<Claims> token = jwtService.parseToken(headerRequest.extractToken());
        Role role = Role.valueOf(token.getPayload().get("role", String.class));
        if (role != Role.ADMIN) 
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can delete users");
        
        usersMicroService.deleteUser(uuid_user);
    }

}
