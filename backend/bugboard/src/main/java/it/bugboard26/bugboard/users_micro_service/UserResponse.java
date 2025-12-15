package it.bugboard26.bugboard.users_micro_service;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResponse {
    private UUID uuid;
    private String name;
    private String surname;
}

