package it.bugboard26.users.services;

import org.springframework.stereotype.Service;

import it.bugboard26.users.dtos.LoginRequest;
import it.bugboard26.users.dtos.RegisterRequest;
import it.bugboard26.users.entities.User;
import it.bugboard26.users.exceptions.InvalidEmailException;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

@AllArgsConstructor
@Service
public class AuthService {

    //Attributes 
    private UserService userService;
    private JwtService jwtService;

    //Methods
    public String verifyAndGenerateToken(LoginRequest loginDTO) {
        User user = userService.verifyUser(loginDTO.getEmail(), loginDTO.getPassword());
        return jwtService.generateJwt(user);
    }

    public User register(RegisterRequest registerDTO) {
        if(userService.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new InvalidEmailException("Email already in use");
        }

        String hashedPassword = BCrypt.hashpw(registerDTO.getPassword(), BCrypt.gensalt());

        User newUser = new User(
            registerDTO.getName(),
            registerDTO.getSurname(),
            registerDTO.getEmail(),
            hashedPassword,
            false 
        );

        userService.save(newUser);
        return newUser;
    }
}
