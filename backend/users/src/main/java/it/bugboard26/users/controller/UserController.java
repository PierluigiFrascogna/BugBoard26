package it.bugboard26.users.controller;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import it.bugboard26.users.dtos.UserResponse;
import it.bugboard26.users.services.UserService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@AllArgsConstructor
@RestController
public class UserController {
    private UserService userService;

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
