package it.bugboard26.bugboard.users_micro_service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// TODO: handle errors in case Users Micro-Service is down
@Service
public class UsersMicroService {
    private String usersServiceUrl;
    private RestTemplate restTemplate;

    public UsersMicroService(@Value("${users-service.url}") String usersServiceUrl, RestTemplate restTemplate) {
        this.usersServiceUrl = usersServiceUrl;
        this.restTemplate = restTemplate;
    }

    public Map<UUID, UserResponse> getUsersByIds(Set<UUID> userIds) {
        String url = usersServiceUrl + "/users/batch";
        HttpEntity<Set<UUID>> requestEntity = new HttpEntity<>(userIds);
        ResponseEntity<UserResponse[]> users = restTemplate.postForEntity(url, requestEntity, UserResponse[].class);

        Map<UUID, UserResponse> userMap = new HashMap<>();
        if (users.getStatusCode().is2xxSuccessful() && users != null) {
            for (UserResponse user : users.getBody()) 
                userMap.put(user.getUuid(), user);
        }

        return userMap;
    }

    
}
