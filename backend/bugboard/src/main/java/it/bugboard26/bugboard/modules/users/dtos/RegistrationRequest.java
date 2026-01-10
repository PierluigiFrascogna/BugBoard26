package it.bugboard26.bugboard.modules.users.dtos;

import it.bugboard26.bugboard.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @Email @NotNull
    private String email;
    @NotBlank @Size(min = 8)
    private String password;
    private Role role;
}
