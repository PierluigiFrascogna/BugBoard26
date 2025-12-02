package it.bugboard26.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class Controller {

    @GetMapping("/")
    public void home() {
        System.out.println("Home microservizio USERS");
    }
    
}
