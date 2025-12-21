package it.bugboard26.bugboard.users_micro_service;

import java.util.UUID;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class UserResponse {
    private UUID uuid;
    private String name;
    private String surname;

    public UserResponse(Jws<Claims> token){
        this.uuid = UUID.fromString(token.getPayload().getSubject());
        this.name = token.getPayload().get("name", String.class);
        this.surname = token.getPayload().get("surname", String.class);
    }
}

