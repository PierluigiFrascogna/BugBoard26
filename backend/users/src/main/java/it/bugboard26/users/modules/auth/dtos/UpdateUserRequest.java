package it.bugboard26.users.modules.auth.dtos;

import java.util.UUID;

import lombok.Getter;

@Getter
public class UpdateUserRequest {
    UUID uuidRequester;
    String email;
    String password;
}
