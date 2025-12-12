package it.bugboard26.bugboard.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class HeaderRequestService {

    private HttpServletRequest request;

    public String getToken() {
        String header = request.getHeader("Authorization");
        if (header == null || header.isBlank() || !StringUtils.startsWithIgnoreCase(header, "Bearer ")) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Authorization header");

        return header.substring(7); // Rimuove "Bearer " dall'inizio della stringa 
    }

}
