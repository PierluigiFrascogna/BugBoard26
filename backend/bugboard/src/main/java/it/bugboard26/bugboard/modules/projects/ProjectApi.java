package it.bugboard26.bugboard.modules.projects;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.bugboard26.bugboard.entities.Change;
import it.bugboard26.bugboard.entities.Comment;
import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.IssueEvent;
import it.bugboard26.bugboard.entities.Project;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.Role;
import it.bugboard26.bugboard.modules.auth.JwtService;
import it.bugboard26.bugboard.modules.issue_events.EventService;
import it.bugboard26.bugboard.modules.issue_events.IssueEventResponse;
import it.bugboard26.bugboard.modules.issue_events.changes.dtos.request.ChangeRequest;
import it.bugboard26.bugboard.modules.issue_events.changes.dtos.response.ChangeResponse;
import it.bugboard26.bugboard.modules.issue_events.comments.CommentRequest;
import it.bugboard26.bugboard.modules.issue_events.comments.CommentResponse;
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
        if (!headerRequest.hasAuthorization()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");

        Jws<Claims> token = jwtService.parseToken(headerRequest.extractToken());
        UUID uuid_user = UUID.fromString(token.getPayload().getSubject());
        List<Project> projects = projectService.getByUserUuid(uuid_user);
        
        List<ProjectResponse> response = new ArrayList<>();
        for (Project project : projects) 
            response.add(ProjectResponse.map(project));
        
        return response;
    }

    @GetMapping("/projects/{uuid_project}") 
    public List<IssueResponse> getIssuesByProject(@PathVariable UUID uuid_project) {
        if (!headerRequest.hasAuthorization()) 
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
         if (!headerRequest.hasAuthorization()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");

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
        if (!headerRequest.hasAuthorization()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");

        Jws<Claims> token = jwtService.parseToken(headerRequest.extractToken());
        UUID uuid_user = UUID.fromString(token.getPayload().getSubject());
        User user = userService.getByUuid(uuid_user);

        if (user.getRole() == Role.VIEWER) 
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient permissions");

        Issue newIssue = issueService.createIssue(issueRequest, uuid_project, user);
        UserResponse author = new UserResponse(token);
        IssueResponse issueResponse = IssueResponse.map(newIssue, author);
        return new ResponseEntity<>(issueResponse, HttpStatus.CREATED);
    }

    @PostMapping("/projects/{uuid_project}/{uuid_issue}")
    public ResponseEntity<CommentResponse> postNewComment(@PathVariable UUID uuid_issue, @RequestBody CommentRequest commentRequest) {
        if (!headerRequest.hasAuthorization()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");

        Jws<Claims> token = jwtService.parseToken(headerRequest.extractToken());
        UUID uuid_user = UUID.fromString(token.getPayload().getSubject());
        User user = userService.getByUuid(uuid_user);

        if(user.getRole() == Role.VIEWER)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient permissions");

        Issue issue = issueService.getByUuid(uuid_issue);
        Comment newComment = eventService.saveComment(commentRequest, issue, user);
        UserResponse author = new UserResponse(token);
        CommentResponse commentResponse = CommentResponse.map(newComment, author);
        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/projects/{uuid_project}/{uuid_issue}")
    public ResponseEntity<ChangeResponse> updateIssue(@PathVariable UUID uuid_issue, @RequestBody ChangeRequest changeRequest) {
        if (!headerRequest.hasAuthorization()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");

        Jws<Claims> token = jwtService.parseToken(headerRequest.extractToken());
        UUID uuid_user = UUID.fromString(token.getPayload().getSubject());
        User user = userService.getByUuid(uuid_user);

        if(user.getRole() == Role.VIEWER)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient permissions");

        Issue issue = issueService.getByUuid(uuid_issue);
        Change change = eventService.saveChange(changeRequest, issue, user);

        UserResponse author = new UserResponse(token);
        ChangeResponse responseChange = ChangeResponse.map(change, author);
        return new ResponseEntity<>(responseChange, HttpStatus.OK);
    }
    
    @PostMapping("/projects")
    public ResponseEntity<ProjectResponse> createNewProject(@RequestBody Map<String, String> body) {
        if (!headerRequest.hasAuthorization()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");

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