package it.bugboard26.bugboard.modules.users;

import java.util.UUID;

import org.springframework.stereotype.Service;

import it.bugboard26.bugboard.entities.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;

    public User getByUuid(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow();
    }
}
