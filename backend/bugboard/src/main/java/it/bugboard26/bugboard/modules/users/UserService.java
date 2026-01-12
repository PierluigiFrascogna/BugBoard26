package it.bugboard26.bugboard.modules.users;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.HttpStatus;

import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.Role;
import it.bugboard26.bugboard.microservices.users.UsersMicroservice;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
    private UsersMicroservice usersMicroservice;
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User getByUuid(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public List<UserResponse> getAllUsers() {
        List<UserResponse> users = usersMicroservice.getAllUsers();
        return this.enrichUsersWithRoles(users);
    }

    private List<UserResponse> enrichUsersWithRoles(List<UserResponse> users) {
        // Estrai tutti gli ID degli utenti
        List<UUID> userIds = users.stream()
            .map(UserResponse::getUuid)
            .toList();
    
        // Recupera gli utenti dal database locale
        Map<UUID, Role> rolesMap = userRepository.findAllByUuidIn(userIds).stream()
            .collect(Collectors.toMap(User::getUuid, User::getRole));
    
        // Arricchisci gli oggetti UserResponse con i ruoli
        users.forEach(user -> {
            Role role = rolesMap.get(user.getUuid());
            user.setRole(role); 
            }
        );

        return users;
    }
    
}
