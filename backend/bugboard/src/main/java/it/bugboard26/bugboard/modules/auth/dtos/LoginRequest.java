package it.bugboard26.bugboard.modules.auth.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {
    private String email;
    private String password;
}
