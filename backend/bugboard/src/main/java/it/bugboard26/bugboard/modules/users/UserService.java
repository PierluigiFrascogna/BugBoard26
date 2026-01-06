package it.bugboard26.bugboard.modules.users;

import java.util.List;
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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

}
