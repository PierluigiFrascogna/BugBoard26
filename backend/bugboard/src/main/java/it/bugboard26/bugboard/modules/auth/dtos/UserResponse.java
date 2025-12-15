package it.bugboard26.bugboard.modules.auth.dtos;

import java.util.UUID;

import it.bugboard26.bugboard.enums.Role;
import lombok.Getter;

@Getter
public class UserResponse {
    private UUID uuid;
    private String name;
    private String surname;
    private Role role;

    // public static UserResponse map(User user) {
    //     UserResponse response = new UserResponse();
    //     response.uuid = user.getUuid();
    //     response.name = getNameFromUserService(response.uuid); // Assume this method fetches the name
    //     response.surname = getSurnameFromUserService(response.uuid); // Assume this method fetches the surname
    //     response.role = user.getRole();
    //     return response;
    // }
}
