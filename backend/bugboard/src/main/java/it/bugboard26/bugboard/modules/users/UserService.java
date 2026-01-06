package it.bugboard26.bugboard.modules.users;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.Role;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import it.bugboard26.bugboard.users_micro_service.UsersMicroService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
    private UsersMicroService usersMicroService;
    private UserRepository userRepository;

    public User getByUuid(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow();
    }

    public List<UserResponse> getAllUsers() {
        List<UserResponse> users = usersMicroService.getAllUsers();
        return this.enrichUsersWithRoles(users);
    }

    
    public User registerUser(User user) {
        return userRepository.save(user);
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
