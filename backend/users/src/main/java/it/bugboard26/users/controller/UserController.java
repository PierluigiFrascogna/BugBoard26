package it.bugboard26.users.controller;

import java.util.Set;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import it.bugboard26.users.dtos.ModifyUserRequest;
import it.bugboard26.users.dtos.UserResponse;
import it.bugboard26.users.entities.User;
import it.bugboard26.users.services.AuthService;
import it.bugboard26.users.services.JwtService;
import it.bugboard26.users.services.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class UserController {
    private JwtService jwtService;
    private UserService userService;
    private AuthService authService;

    @PutMapping("/users/me")
    public String updateUser(@RequestHeader("Authorization") String authHeader, @RequestBody ModifyUserRequest request) {
        User user = authService.getAuthenticatedUser(authHeader);
        if (request.getName() != null) user.setName(request.getName());
        if (request.getSurname() != null) user.setSurname(request.getSurname());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getRawPassword() != null) user.setPasswordHash(BCrypt.hashpw(request.getRawPassword(), BCrypt.gensalt()));
        User updatedUser = userService.save(user);
        return jwtService.generateJwt(updatedUser);

    }

    @PostMapping("/users/batch")
    public UserResponse[] getUsersBatch(@RequestBody Set<UUID> user_ids) {
        return userService.findAllByIds(user_ids);
    }
    
    
}
