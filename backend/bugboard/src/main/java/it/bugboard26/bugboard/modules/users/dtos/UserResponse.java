package it.bugboard26.bugboard.modules.users.dtos;

import java.util.UUID;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import it.bugboard26.bugboard.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter @Setter
@ToString
public class UserResponse {
    private UUID uuid;
    private String name;
    private String surname;
    private String email;
    private Role role;

    public UserResponse(Jws<Claims> token){
        this.uuid = UUID.fromString(token.getPayload().getSubject());
        this.name = token.getPayload().get("name", String.class);
        this.surname = token.getPayload().get("surname", String.class);
        this.email = token.getPayload().get("email", String.class);
        this.role = Role.valueOf(token.getPayload().get("role", String.class));
    }
}

