package it.bugboard26.bugboard.users_micro_service;

import it.bugboard26.bugboard.enums.Role;
import it.bugboard26.bugboard.modules.users.dtos.RegistrationRequest;
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

    public UserServiceRequest(RegistrationRequest frontendRequest) {
        this.name = frontendRequest.getName();
        this.surname = frontendRequest.getSurname();
        this.email = frontendRequest.getEmail();
        this.password = frontendRequest.getPassword();
        this.isAdmin = (frontendRequest.getRole() == Role.ADMIN) ? true : false; 
    }
}