package it.bugboard26.bugboard.modules.auth.dtos;

import java.util.UUID;

import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.Role;
import lombok.Getter;

@Getter
public class UserResponse {
    private UUID uuid;
    private Role role;

    public static UserResponse map(User user) {
        UserResponse response = new UserResponse();
        response.uuid = user.getUuid();
        response.role = user.getRole();
        return response;
    }
}
