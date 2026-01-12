package it.bugboard26.bugboard.auth;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import it.bugboard26.bugboard.enums.Role;

@RequestScope
@Component
public class Jwt {
    private Jws<Claims> token;

    public Jws<Claims> getToken() {
        return token;
    }

    public void setToken(Jws<Claims> token) {
        this.token = token;
    }

    public UUID getUserUuid() {
        return UUID.fromString(token.getPayload().getSubject());
    }

    public Role getRole() {
        return Role.valueOf(token.getPayload().get("role", String.class));
    }
}
