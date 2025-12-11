package it.bugboard26.bugboard.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import it.bugboard26.bugboard.dtos.RegistrationRequest;
import it.bugboard26.bugboard.dtos.UserServiceRequest;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.Role;
import it.bugboard26.bugboard.repositories.AuthRepository;

import org.springframework.http.ResponseEntity; 
import org.springframework.http.HttpStatus;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;  

@Service
public class AuthService {

    //Attributes
    private final String userServiceURL;
    private final RestTemplate restTemplate;
    private final AuthRepository authRepository;

    //Constructor
    public AuthService(@Value("${users-service.url}") String userServiceURL, RestTemplate restTemplate, AuthRepository authRepository) {
        this.userServiceURL = userServiceURL;
        this.restTemplate = restTemplate;
        this.authRepository = authRepository;
    }

    //Methods
    public UUID registerUserViaMicroservice(String authToken, RegistrationRequest frontendRequest) {
    
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

        // 4. Invio della Richiesta
        try {
            ResponseEntity<UUID> response = restTemplate.postForEntity(
                registrationURL,
                entity,
                UUID.class
            );

            // 5. Controllo Status Code
            if (response.getStatusCode() != HttpStatus.OK) 
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to register user: Status " + response.getStatusCode());
            

            UUID uuid = response.getBody();
            return uuid;
        } 
        catch (HttpClientErrorException.Forbidden e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        catch (HttpClientErrorException.Conflict e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }

        catch (HttpClientErrorException.BadRequest e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid registration data");
        }

        catch (HttpStatusCodeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error: " + e.getMessage());
        }
    }

    public void registerUser(User user) {
        authRepository.save(user);
    }
}