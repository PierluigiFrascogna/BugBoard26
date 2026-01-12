package it.bugboard26.bugboard.modules.projects;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

import it.bugboard26.bugboard.auth.Jwt;
import it.bugboard26.bugboard.entities.Project;
import it.bugboard26.bugboard.enums.Role;
import it.bugboard26.bugboard.modules.projects.dtos.ProjectRequest;
import it.bugboard26.bugboard.modules.projects.dtos.ProjectResponse;
import lombok.AllArgsConstructor;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@AllArgsConstructor
@RestController
public class ProjectApi {
    private Jwt jwtUser;
    private ProjectService projectService;

    @GetMapping("/projects")
    public List<ProjectResponse> getProjects() {
        List<Project> projects = projectService.getAllProjects();
        return projects.stream()
                .map(project -> ProjectResponse.map(project))
                .toList();
    }
    
    @PostMapping("/projects")
    public ProjectResponse createNewProject(@RequestBody ProjectRequest projectRequest) {
        if(jwtUser.getRole() != Role.ADMIN)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient permissions");

        Project newProject = projectService.createProject(projectRequest.getName());
        return ProjectResponse.map(newProject);
    }
    
}