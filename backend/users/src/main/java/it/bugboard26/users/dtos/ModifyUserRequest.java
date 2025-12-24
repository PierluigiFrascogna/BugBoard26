package it.bugboard26.users.dtos;

import lombok.Getter;

@Getter
public class ModifyUserRequest {
    String name;
    String surname;
    String email;
    String rawPassword;
}
