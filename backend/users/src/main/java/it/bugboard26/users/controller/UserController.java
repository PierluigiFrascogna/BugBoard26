package it.bugboard26.users.controller;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import it.bugboard26.users.dtos.ModifyUserRequest;
import it.bugboard26.users.dtos.UserResponse;
import it.bugboard26.users.entities.User;
import it.bugboard26.users.services.AuthService;
import it.bugboard26.users.services.UserService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@AllArgsConstructor
@RestController
public class UserController {
    private UserService userService;

    //TODO: implementare update user
    // @PutMapping("/users/me")
    // public UserResponse updateUser(@RequestBody ModifyUserRequest request) {
    //     User user = authService.getAuthenticatedUser();
    //     if (request.getName() != null) user.setName(request.getName());
    //     if (request.getSurname() != null) user.setSurname(request.getSurname());
    //     if (request.getEmail() != null) user.setEmail(request.getEmail());
    //     if (request.getRawPassword() != null) user.setPasswordHash(BCrypt.hashpw(request.getRawPassword(), BCrypt.gensalt()));
    //     User updatedUser = userService.save(user);
    //     return jwtService.generateJwt(updatedUser);
    //     return null;

    // }

    @PostMapping("/users/batch")
    public UserResponse[] getUsersBatch(@RequestBody Set<UUID> user_ids) {
        return userService.findAllByIds(user_ids);
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userService.findAll();
    }

    @DeleteMapping("/users/{uuid_user}")
    public void deleteUser(@PathVariable UUID uuid_user) {
        userService.deleteUser(uuid_user);
    }
    
}
