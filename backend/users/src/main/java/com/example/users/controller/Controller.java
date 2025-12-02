package com.example.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
public class Controller {

    @GetMapping("/sdf")
    public String home() {
        System.out.println("Home microservizio USERS");
        return "indexe";
    }
    
}
