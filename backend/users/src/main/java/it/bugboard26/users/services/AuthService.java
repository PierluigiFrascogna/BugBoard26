package it.bugboard26.users.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.bugboard26.users.dtos.LoginRequest;
import it.bugboard26.users.dtos.UpdateUserRequest;
import it.bugboard26.users.entities.User;
import lombok.AllArgsConstructor;

import org.mindrot.jbcrypt.BCrypt;

@AllArgsConstructor
@Service
public class AuthService {
    private UserService userService;

    public User login(LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());
        if(!BCrypt.checkpw(loginRequest.getPassword(), user.getPasswordHash()) || !user.isActive()) 
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");

        return user;
    }

    public User updateUser(UpdateUserRequest updateRequest) {
        User user = userService.findByUuid(updateRequest.getUuidRequester());

        if(updateRequest.getEmail() != null) {
            if(userService.existsByEmail(updateRequest.getEmail())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
            }
            user.setEmail(updateRequest.getEmail());
        }
        
        if(updateRequest.getPassword() != null) {
            String hashedPassword = BCrypt.hashpw(updateRequest.getPassword(), BCrypt.gensalt());
            user.setPasswordHash(hashedPassword);
        }

        return userService.save(user);
    }

}
