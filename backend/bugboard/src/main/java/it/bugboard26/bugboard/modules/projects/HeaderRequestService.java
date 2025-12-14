package it.bugboard26.bugboard.modules.projects;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class HeaderRequestService {

    private HttpServletRequest request;

    public boolean hasAuthorizationHeader() {
        String header = request.getHeader("Authorization");
        if(header == null || header.isBlank() || !header.toLowerCase().startsWith("bearer ")) 
            return false;
        else
            return true;
    }

    public String getToken() {
        String header = request.getHeader("Authorization");            
        return header.substring(7); // Rimuove "Bearer " dall'inizio della stringa 
    }

}
