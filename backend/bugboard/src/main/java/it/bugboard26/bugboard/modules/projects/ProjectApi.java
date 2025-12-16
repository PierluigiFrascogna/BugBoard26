package it.bugboard26.bugboard.modules.projects;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.IssueEvent;
import it.bugboard26.bugboard.entities.Project;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.Role;
import it.bugboard26.bugboard.modules.auth.JwtService;
import it.bugboard26.bugboard.modules.issue_events.EventService;
import it.bugboard26.bugboard.modules.issue_events.IssueEventResponse;
import it.bugboard26.bugboard.modules.issues.IssueService;
import it.bugboard26.bugboard.modules.issues.dtos.IssueRequest;
import it.bugboard26.bugboard.modules.issues.dtos.IssueResponse;
import it.bugboard26.bugboard.modules.users.UserService;
import it.bugboard26.bugboard.users_micro_service.UserResponse;
import it.bugboard26.bugboard.users_micro_service.UsersMicroService;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@AllArgsConstructor
@RestController
public class ProjectApi {
    HeaderRequestService headerRequest;
    JwtService jwtService;
    UserService userService;
    UsersMicroService usersMicroService;
    ProjectService projectService;
    IssueService issueService;
    EventService eventService;

    @GetMapping("/projects")
    public List<ProjectResponse> getProjectsByUser() {
        if (!headerRequest.hasAuthorizationHeader()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");

        Jws<Claims> token = jwtService.parseToken(headerRequest.extractToken());
        List<Project> projects = projectService.getByUserUuid(jwtService.getUUID(token));
        
        List<ProjectResponse> response = new ArrayList<>();
        for (Project project : projects) 
            response.add(ProjectResponse.map(project));
        
        return response;
    }

    @GetMapping("/projects/{uuid_project}") 
    public List<IssueResponse> getIssuesByProject(@PathVariable UUID uuid_project) {
        if (!headerRequest.hasAuthorizationHeader()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");
        
        List<Issue> issues = issueService.getAllByProjectUuid(uuid_project);
        
        Set<UUID> authorIds = new HashSet<>();
        for (Issue issue : issues) 
            authorIds.add(issue.getAuthor().getUuid());   
        
        Map<UUID, UserResponse> authors = usersMicroService.getUsersByIds(authorIds);

        List<IssueResponse> response = new ArrayList<>();
        for (Issue issue : issues){
            UserResponse author = authors.get(issue.getAuthor().getUuid());
            response.add(IssueResponse.map(issue, author));
        }
            
        return response;
    }

    @GetMapping("/projects/{uuid_project}/{uuid_issue}")
    public List<IssueEventResponse> getEventsByIssue(@PathVariable UUID uuid_project, @PathVariable UUID uuid_issue) {
        List<IssueEvent> events = eventService.getByIssueUuid(uuid_issue);

        Set<UUID> authorIds = new HashSet<>();
        for (IssueEvent event : events) 
            authorIds.add(event.getAuthor().getUuid());

        Map<UUID, UserResponse> authors = usersMicroService.getUsersByIds(authorIds);

        List<IssueEventResponse> response = new ArrayList<>();
        for (IssueEvent event : events){
            UserResponse author = authors.get(event.getAuthor().getUuid());
            response.add(IssueEventResponse.map(event, author));
        }
        
        return response;
    }


    @PostMapping("/projects/{uuid_project}")
    public ResponseEntity<IssueResponse> postNewIssue(@PathVariable UUID uuid_project, @RequestBody IssueRequest issueRequest) {
        if (!headerRequest.hasAuthorizationHeader()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");

        Jws<Claims> token = jwtService.parseToken(headerRequest.extractToken());

        if(jwtService.getRole(token) == Role.VIEWER)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient permissions");


        User author = userService.getByUuid(jwtService.getUUID(token));
        Project project = projectService.getByUuid(uuid_project);
        Issue newIssue = IssueRequest.mapToEntity(issueRequest, author, project);
        Issue savedIssue = issueService.save(newIssue);

        UserResponse authorResponse = new UserResponse(jwtService.getUUID(token), jwtService.getName(token), jwtService.getSurname(token));
        IssueResponse issueResponse = IssueResponse.map(savedIssue, authorResponse);
        return new ResponseEntity<>(issueResponse, HttpStatus.CREATED);
    }
    
}