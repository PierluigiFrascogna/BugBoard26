package it.bugboard26.bugboard.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.bugboard26.bugboard.dtos.IssueEventResponse;
import it.bugboard26.bugboard.dtos.IssueResponse;
import it.bugboard26.bugboard.dtos.ProjectResponse;
import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.IssueEvent;
import it.bugboard26.bugboard.entities.Project;
import it.bugboard26.bugboard.services.EventService;
import it.bugboard26.bugboard.services.HeaderRequestService;
import it.bugboard26.bugboard.services.IssueService;
import it.bugboard26.bugboard.services.JwtService;
import it.bugboard26.bugboard.services.ProjectService;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
public class ProjectApi {
    HeaderRequestService headerService;
    JwtService jwtService;
    ProjectService projectService;
    IssueService issueService;
    EventService eventService;

    @GetMapping("/projects")
    public List<ProjectResponse> getProjectsByUser() {
        if (!headerService.hasAuthorizationHeader()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");

        String jwtToken = headerService.getToken();
        UUID uuid = jwtService.getUUID(jwtToken);
        List<Project> projects = projectService.getByUserUuid(uuid);
        
        List<ProjectResponse> response = new ArrayList<>();
        for (Project project : projects) 
            response.add(ProjectResponse.map(project));
        
        return response;
    }

    @GetMapping("/projects/{uuid_project}") 
    public List<IssueResponse> getIssuesByProject(@PathVariable UUID uuid_project) {
        if (!headerService.hasAuthorizationHeader()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");
        
        List<Issue> issues = issueService.getByProjectUuid(uuid_project);

        List<IssueResponse> response = new ArrayList<>();
        for (Issue issue : issues)
            response.add(IssueResponse.map(issue));
            
        return response;
    }

    @GetMapping("/projects/{uuid_project}/{uuid_issue}")
    public List<IssueEventResponse> getEventsByIssue(@PathVariable UUID uuid_project, @PathVariable UUID uuid_issue) {
        List<IssueEvent> events = eventService.getByIssueUuid(uuid_issue);

        List<IssueEventResponse> response = new ArrayList<>();
        for (IssueEvent event : events)
            response.add(IssueEventResponse.map(event));

        
        return response;
    }
    

}