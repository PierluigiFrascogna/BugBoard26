package it.bugboard26.bugboard.modules.users;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.bugboard26.bugboard.auth.Jwt;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.Role;
import it.bugboard26.bugboard.microservices.users.UsersMicroservice;
import it.bugboard26.bugboard.modules.users.dtos.RegistrationRequest;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/users")
@RestController
public class UserApi {
    private Jwt jwtUser;
    private UserService userService;
    private UsersMicroservice usersMicroService;

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        if (jwtUser.getRole() != Role.ADMIN) 
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can register new users");
        
        UUID uuid_newUser = usersMicroService.registerUser(registrationRequest);
        User newUser = new User(uuid_newUser, registrationRequest.getRole());
        userService.registerUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        if (jwtUser.getRole() != Role.ADMIN) 
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can get all users");
       
        return userService.getAllUsers();
    }

    @DeleteMapping("/{uuid_user}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID uuid_user) {
        if (jwtUser.getRole() != Role.ADMIN) 
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can delete users");
        
        usersMicroService.deleteUser(uuid_user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
