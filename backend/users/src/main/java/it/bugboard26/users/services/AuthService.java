package it.bugboard26.users.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.bugboard26.users.dtos.LoginRequest;
import it.bugboard26.users.dtos.RegistrationRequest;
import it.bugboard26.users.dtos.UpdateUserRequest;
import it.bugboard26.users.entities.User;
import lombok.AllArgsConstructor;

import org.mindrot.jbcrypt.BCrypt;

@AllArgsConstructor
@Service
public class AuthService {
    private UserService userService;

    public User login(LoginRequest loginDTO) {
        User user = userService.findByEmail(loginDTO.getEmail());
        if(!BCrypt.checkpw(loginDTO.getPassword(), user.getPasswordHash())) 
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");

        return user;
    }

    public User register(RegistrationRequest frontendRequest) {
        if(userService.existsByEmail(frontendRequest.getEmail())) 
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");

        String hashedPassword = BCrypt.hashpw(frontendRequest.getPassword(), BCrypt.gensalt());
        User newUser = new User(
            frontendRequest.getName(),
            frontendRequest.getSurname(),
            frontendRequest.getEmail(),
            hashedPassword,
            frontendRequest.isAdmin()
        );
        userService.save(newUser);
        return newUser;
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
