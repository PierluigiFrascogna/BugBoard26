package it.bugboard26.users.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import it.bugboard26.users.dtos.RegistrationRequest;
import it.bugboard26.users.dtos.UserResponse;
import it.bugboard26.users.entities.User;
import it.bugboard26.users.repositories.UserRepository;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByUuid(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserResponse[] findAllByIds(Set<UUID> user_ids) {
         List<User> users = userRepository.findAllById(user_ids);
         
         List<UserResponse> response = new ArrayList<>();
         for (User user : users) 
            response.add(UserResponse.map(user));
         
         return response.toArray(new UserResponse[0]);
    }

    public List<UserResponse> findAll() {
        return userRepository.findAllByIsActiveTrue().stream()
        .map(user -> UserResponse.map(user))
        .toList();
    }

    public User registerUser(RegistrationRequest frontendRequest) {
        if(this.existsByEmail(frontendRequest.getEmail())) 
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");

        String hashedPassword = BCrypt.hashpw(frontendRequest.getPassword(), BCrypt.gensalt());
        User newUser = new User(
            frontendRequest.getName(),
            frontendRequest.getSurname(),
            frontendRequest.getEmail(),
            hashedPassword,
            frontendRequest.isAdmin()
        );
        
        return this.save(newUser);
    }

    public void deleteUser(UUID uuid_user) {
        User user = this.findByUuid(uuid_user);
        user.setActive(false);
        userRepository.save(user);
    }

}
