package it.bugboard26.users.services;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import it.bugboard26.users.entities.User;
import it.bugboard26.users.repositories.UserRepository;
import it.bugboard26.users.exceptions.AuthenticationException;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new AuthenticationException("User not found"));
    }
}
