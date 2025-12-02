package com.example.bugboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
    
@RestController
public class Controller {

    @GetMapping("/prova")
    public void home() {
        System.out.println("Home microservizio BUGBOARD");
    }
}
