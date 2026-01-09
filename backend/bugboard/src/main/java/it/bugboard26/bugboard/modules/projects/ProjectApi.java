package it.bugboard26.bugboard.modules.projects;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import it.bugboard26.bugboard.entities.Project;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.Role;
import it.bugboard26.bugboard.modules.auth.JwtService;
import it.bugboard26.bugboard.modules.users.UserService;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@AllArgsConstructor
@CrossOrigin(origins = "https://app.bugboard26.it")
@RestController
public class ProjectApi {
    HeaderRequestService headerRequest;
    JwtService jwtService;
    UserService userService;
    ProjectService projectService;

    @GetMapping("/projects")
    public List<ProjectResponse> getProjects() {
        if (!headerRequest.hasAuthorization()) 
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");

        List<Project> projects = projectService.getAllProjects();
        List<ProjectResponse> projectsResponse = new ArrayList<>();
        for (Project project : projects) 
            projectsResponse.add(ProjectResponse.map(project));
        
        return projectsResponse;
    }

    
    @PostMapping("/projects")
    public ResponseEntity<ProjectResponse> createNewProject(@RequestBody Map<String, String> body) {
        if (!headerRequest.hasAuthorization()) 
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");

        Jws<Claims> token = jwtService.parseToken(headerRequest.extractToken());
        UUID uuid_user = UUID.fromString(token.getPayload().getSubject());
        User user = userService.getByUuid(uuid_user);

        if(user.getRole() != Role.ADMIN)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient permissions");

        String projectName = body.get("name");
        Project newProject = projectService.createProject(projectName);
        ProjectResponse projectResponse = ProjectResponse.map(newProject);
        return new ResponseEntity<>(projectResponse, HttpStatus.CREATED);
    }
    
}