package it.bugboard26.bugboard.api;

import org.springframework.web.bind.annotation.RestController;

import it.bugboard26.bugboard.dtos.IssueResponse;
import it.bugboard26.bugboard.dtos.ProjectResponse;
import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.Project;
import it.bugboard26.bugboard.services.HeaderRequestService;
import it.bugboard26.bugboard.services.IssueService;
import it.bugboard26.bugboard.services.JwtService;
import it.bugboard26.bugboard.services.ProjectService;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@AllArgsConstructor
@RestController
public class ProjectApi {
    HeaderRequestService headerService;
    JwtService jwtService;
    ProjectService projectService;
    IssueService issueService;

    @GetMapping("/projects")
    public Set<ProjectResponse> getProjectsByUser() {
        String jwtToken = headerService.getToken();
        UUID uuid = jwtService.getUUID(jwtToken);
        Set<Project> projects = projectService.getByUserUuid(uuid);
        
        Set<ProjectResponse> response = new HashSet<>();
        for (Project project : projects) 
            response.add(new ProjectResponse(project));
        
        return response;
    }

    @GetMapping("/projects/{uuid_project}") 
    public List<IssueResponse> getProjectByUuid(@PathVariable UUID uuid_project) {
        List<Issue> issues = issueService.getByProjectUuid(uuid_project);
        List<IssueResponse> response = new ArrayList<IssueResponse>();
        for (Issue issue : issues)
                response.add(new IssueResponse().mapToResponse(issue));
            
            return response;
    }

    @GetMapping("/projects/{uuid_project}/{uuid_issue}")
    public String getMethodName(@PathVariable UUID uuid_project, @PathVariable UUID uuid_issue) {
        return new String("ciao");
    }
    

}