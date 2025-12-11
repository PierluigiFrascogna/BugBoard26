package it.bugboard26.users.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private boolean isAdmin;
}
