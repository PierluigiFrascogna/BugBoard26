package it.bugboard26.users.modules.auth.dtos;

import java.util.UUID;

import it.bugboard26.users.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResponse {
    private UUID uuid;
    private String name;
    private String surname;
    private String email;

    public static UserResponse map(User user) {
        UserResponse response = new UserResponse();
        response.uuid = user.getUuid();
        response.name = user.getName();
        response.surname = user.getSurname();
        response.email = user.getEmail();
        return response;
    }
}
