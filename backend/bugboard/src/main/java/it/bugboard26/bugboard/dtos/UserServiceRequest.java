package it.bugboard26.bugboard.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserServiceRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private boolean isAdmin; 
}