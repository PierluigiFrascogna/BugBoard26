package it.bugboard26.users.controller;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.bugboard26.users.entities.User;
import it.bugboard26.users.services.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/users")
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

        User user = new User("Marco", "Festa", "marco@bugboard26.it", password, false);
        userService.save(user);
        

    }
    
}
