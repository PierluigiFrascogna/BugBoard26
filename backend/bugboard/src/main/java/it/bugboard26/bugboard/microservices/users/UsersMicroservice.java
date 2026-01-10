package it.bugboard26.bugboard.microservices.users;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import it.bugboard26.bugboard.microservices.users.dtos.UserMicroserviceRequest;
import it.bugboard26.bugboard.modules.auth.dtos.LoginRequest;
import it.bugboard26.bugboard.modules.auth.dtos.UpdateUserRequest;
import it.bugboard26.bugboard.modules.users.dtos.RegistrationRequest;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import jakarta.validation.Valid;

// TODO: handle errors in case Users Micro-Service is down
@Service
public class UsersMicroservice {
    //Attributes
    private String userServiceURL;
    private String USERS_URL = "/users";
    private String AUTH_URL = "/auth";
    private RestClient restClient;

    //Constructor
    public UsersMicroservice(@Value("${users-service.url}") String usersServiceUrl, RestClient restClient) {
        this.userServiceURL = usersServiceUrl;
        this.restClient = restClient;
    }

    //Methods
    public Map<UUID, UserResponse> getUsersByIds(Set<UUID> userIds) {
        String usersBatchURL = userServiceURL + USERS_URL + "/batch";
        try {
            List<UserResponse> users = restClient.post()
                                                .uri(usersBatchURL)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .body(userIds)
                                                .retrieve()
                                                .body(new ParameterizedTypeReference<List<UserResponse>>() {});
        
            return users.stream()
            .collect(Collectors.toMap(
                user -> user.getUuid(),
                user -> user
            )); 
        }
        catch (HttpStatusCodeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error: " + e.getMessage());
        }
    }

    public UUID registerUser(RegistrationRequest frontendRequest) {
        UserMicroserviceRequest backendRequest = new UserMicroserviceRequest(frontendRequest);
        String registrationURL = userServiceURL + USERS_URL;
        try {
            return restClient.post()
                            .uri(registrationURL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(backendRequest)
                            .retrieve()
                            .body(UUID.class);            
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
        String loginURL = userServiceURL + AUTH_URL + "/login";
        try {
            return restClient.post()
                            .uri(loginURL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(loginRequest)
                            .retrieve()
                            .body(new ParameterizedTypeReference<UserResponse>() {});
            
        } 
        catch (HttpClientErrorException.Unauthorized e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        } 
        catch (HttpStatusCodeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error: " + e.getMessage());
        }
    }

    public List<UserResponse> getAllUsers() {
        String allUsersURL = userServiceURL + USERS_URL;
        return restClient.get()
                        .uri(allUsersURL)
                        .retrieve()
                        .body(new ParameterizedTypeReference<List<UserResponse>>() {});
    }

    public void updateUser(UpdateUserRequest updateUserRequest) {
        String updateURL = userServiceURL + AUTH_URL;
        try{
            restClient.patch()
                        .uri(updateURL)
                        .contentType(MediaType.APPLICATION_JSON) 
                        .body(updateUserRequest)                        
                        .retrieve()
                        .toBodilessEntity();           
        } 
        catch (HttpClientErrorException.Conflict e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }
        catch (HttpStatusCodeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error: " + e.getMessage());  
        }
    }


    public void deleteUser(UUID uuid_user) {
        String deleteURL = userServiceURL + USERS_URL + "/" + uuid_user.toString();
        restClient.delete()
                    .uri(deleteURL)
                    .retrieve()
                    .toBodilessEntity();
    }
}
