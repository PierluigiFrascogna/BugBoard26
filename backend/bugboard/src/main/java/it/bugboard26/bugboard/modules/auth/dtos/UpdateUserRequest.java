package it.bugboard26.bugboard.modules.auth.dtos;

import java.util.UUID;

import lombok.Getter;

@Getter
public class UpdateUserRequest {
    UUID uuidRequester;
    String email;
    String password;

    public void setUuidRequester(UUID uuidRequester) {
        this.uuidRequester = uuidRequester;
    }
}
