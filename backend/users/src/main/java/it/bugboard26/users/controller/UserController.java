package it.bugboard26.users.controller;

import java.util.Set;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import it.bugboard26.users.dtos.UserResponse;
import it.bugboard26.users.entities.User;
import it.bugboard26.users.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@AllArgsConstructor
@RestController
public class UserController {
    UserService userService;

    @GetMapping("/")
    public void home() {
        System.out.println("Home microservizio USERS");
    }

    @GetMapping("/save")
    public void save() {
        System.out.println("Adesso proverò a salvare un utente nel database");

        String password = BCrypt.hashpw("1234", BCrypt.gensalt());

        User user = new User("Alessandro", "Giglio", "alessandro@bugboard26.it", password, true);
        userService.save(user);
    }

    @PostMapping("/users/batch")
    public UserResponse[] getUsersBatch(@RequestBody Set<UUID> user_ids) {
        return userService.findAllByIds(user_ids);
    }
    
    
}
