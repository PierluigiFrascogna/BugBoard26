package it.bugboard26.bugboard.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.bugboard26.bugboard.dtos.RegistrationRequest;
import it.bugboard26.bugboard.dtos.UserServiceRequest;
import it.bugboard26.bugboard.enums.Role;
import it.bugboard26.bugboard.exceptions.ForbiddenException;

import org.springframework.http.ResponseEntity; 
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;  

@Service
public class AuthService {

    @Value("${users-service.url}")
    private String userServiceURL;

    private final RestTemplate restTemplate = new RestTemplate();

   // Assicurati di passare il token "Bearer ..." completo a questo metodo dal Controller
    public String registerUserViaMicroservice(String authToken, RegistrationRequest frontendRequest) {
    
        // 1. Preparazione del Body
        UserServiceRequest backendRequest = new UserServiceRequest();
        backendRequest.setName(frontendRequest.getName());
        backendRequest.setSurname(frontendRequest.getSurname());
        backendRequest.setEmail(frontendRequest.getEmail());
        backendRequest.setPassword(frontendRequest.getPassword());
        
        if (frontendRequest.getRole() == Role.ADMIN) 
            backendRequest.setAdmin(true);
        else 
            backendRequest.setAdmin(false);
        
        String registrationURL = userServiceURL + "/auth/register";
        
        // 2. Preparazione degliHeaders (FIX)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authToken); // Qui passiamo il token ricevuto
        
        // 3. Creazione dell'Entity (Header + Body)
        HttpEntity<UserServiceRequest> entity = new HttpEntity<>(backendRequest, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                registrationURL,
                entity,
                String.class
            );

            // 5. Controllo Status Code
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new ForbiddenException("Failed to register user: Status " + response.getStatusCode());
            }

            return response.getBody();

        } 
        catch (Exception e) {
            // Gestione errori generici
            throw new RuntimeException("Errore comunicazione interno: " + e.getMessage());
        }
    }
}