package it.bugboard26.users.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.bugboard26.users.dtos.LoginRequest;
import it.bugboard26.users.dtos.RegistrationRequest;
import it.bugboard26.users.entities.User;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

@AllArgsConstructor
@Service
public class AuthService {

    //Attributes 
    private UserService userService;
    private JwtService jwtService;

    //Methods
    public String verifyAndGenerateToken(LoginRequest frontendRequest) {
        User user = userService.verifyUser(frontendRequest.getEmail(), frontendRequest.getPassword());
        return jwtService.generateJwt(user);
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
}
