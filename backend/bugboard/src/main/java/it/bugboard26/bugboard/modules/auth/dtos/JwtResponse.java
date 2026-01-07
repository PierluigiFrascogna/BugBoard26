package it.bugboard26.bugboard.modules.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtResponse {
    private String token;
}
