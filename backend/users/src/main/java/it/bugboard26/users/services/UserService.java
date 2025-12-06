package it.bugboard26.users.services;

import org.springframework.stereotype.Service;

import it.bugboard26.users.entities.User;
import it.bugboard26.users.repositories.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }
}
