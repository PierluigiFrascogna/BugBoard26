package it.bugboard26.bugboard.users_micro_service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import it.bugboard26.bugboard.modules.auth.dtos.LoginRequest;
import it.bugboard26.bugboard.modules.users.dtos.RegistrationRequest;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;

// TODO: handle errors in case Users Micro-Service is down
@Service
public class UsersMicroService {
    //Attributes
    private String userServiceURL;
    private RestTemplate restTemplate;
    private RestClient restClient;

    //Constructor
    public UsersMicroService(@Value("${users-service.url}") String usersServiceUrl, RestTemplate restTemplate, RestClient restClient) {
        this.userServiceURL = usersServiceUrl;
        this.restTemplate = restTemplate;
        this.restClient = restClient;
    }

    //Methods
    public Map<UUID, UserResponse> getUsersByIds(Set<UUID> userIds) {
        String url = userServiceURL + "/users/batch";
        ResponseEntity<UserResponse[]> users = restTemplate.postForEntity(url, userIds, UserResponse[].class);

        Map<UUID, UserResponse> userMap = new HashMap<>();
        if (users.getStatusCode().is2xxSuccessful() && users != null) {
            for (UserResponse user : users.getBody()) 
                userMap.put(user.getUuid(), user);
        }

        return userMap;
    }

    public UUID registerUser(RegistrationRequest frontendRequest) {
        UserMicroserviceRequest backendRequest = new UserMicroserviceRequest(frontendRequest);
        String registrationURL = userServiceURL + "/auth/register";
        try {
            ResponseEntity<UUID> response = restTemplate.postForEntity(
                registrationURL,
                backendRequest,
                UUID.class
            );
            
            UUID uuid_newUser = response.getBody();
            return uuid_newUser;
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

    public UserResponse loginUser(LoginRequest loginRequest) {
        String loginURL = userServiceURL + "/auth/login";
        try {
            ResponseEntity<UserResponse> response = restTemplate.postForEntity(
                loginURL,
                loginRequest,
                UserResponse.class
            );
            
            UserResponse userResponse = response.getBody();
            return userResponse;
        } 
        catch (HttpClientErrorException.Unauthorized e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        } 
        catch (HttpStatusCodeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error: " + e.getMessage());
        }
    }

    public List<UserResponse> getAllUsers() {
        String url = userServiceURL + "/users";
        return restClient.get()
                        .uri(url)
                        .retrieve()
                        .body(new ParameterizedTypeReference<List<UserResponse>>() {});
        
    }

}
