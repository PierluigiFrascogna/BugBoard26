package it.bugboard26.users.dtos;

import java.util.UUID;

import it.bugboard26.users.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private UUID uuid;
    private String name;
    private String surname;
    private boolean isAdmin;
    private String email;

    public UserResponse(User userEntity) {
        this.uuid = userEntity.getUuid();
        this.name = userEntity.getName();
        this.surname = userEntity.getSurname();
        this.isAdmin = userEntity.isAdmin();
        this.email = userEntity.getEmail();
    }
}
