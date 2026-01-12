package it.bugboard26.bugboard.auth;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private JwtService jwtService;
    private Jwt jwtContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Lascia passare le richieste OPTIONS senza controllare il JWT
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true; 
        }
        
        String header = request.getHeader("Authorization");
        if (header == null || !header.toLowerCase().startsWith("bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        }

        String tokenString = header.substring(7);

        try {
            Jws<Claims> token = jwtService.validateAndParseToken(tokenString);
            jwtContext.setToken(token);
            return true; 
        } 
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
    }
}