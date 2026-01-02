package it.bugboard26.bugboard.modules.auth;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.modules.auth.dtos.RegistrationRequest;
import it.bugboard26.bugboard.modules.auth.dtos.UserServiceRequest;

import org.springframework.http.ResponseEntity; 
import org.springframework.http.HttpStatus;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

@Service
public class AuthService {
    private String userServiceURL;
    private RestTemplate restTemplate;
    private AuthRepository authRepository;

    //Constructor
    public AuthService(@Value("${users-service.url}") String userServiceURL, RestTemplate restTemplate, AuthRepository authRepository) {
        this.userServiceURL = userServiceURL;
        this.restTemplate = restTemplate;
        this.authRepository = authRepository;
    }

    //Methods
    public UUID registerUserViaMicroservice(RegistrationRequest frontendRequest) {
        UserServiceRequest backendRequest = new UserServiceRequest(frontendRequest);
        String registrationURL = userServiceURL + "/auth/register";
        try {
            ResponseEntity<UUID> response = restTemplate.postForEntity(
                registrationURL,
                backendRequest,
                UUID.class
            );
            
            UUID uuid = response.getBody();
            return uuid;
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