package it.bugboard26.bugboard.modules.auth.dtos;

import it.bugboard26.bugboard.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private Role role;
}
