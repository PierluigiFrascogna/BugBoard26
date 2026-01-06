package it.bugboard26.bugboard.modules.issue_events;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import it.bugboard26.bugboard.entities.Change;
import it.bugboard26.bugboard.entities.Comment;
import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.IssueEvent;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.Role;
import it.bugboard26.bugboard.modules.auth.JwtService;
import it.bugboard26.bugboard.modules.issue_events.changes.dtos.request.ChangeRequest;
import it.bugboard26.bugboard.modules.issue_events.changes.dtos.response.ChangeResponse;
import it.bugboard26.bugboard.modules.issue_events.comments.CommentRequest;
import it.bugboard26.bugboard.modules.issue_events.comments.CommentResponse;
import it.bugboard26.bugboard.modules.issues.IssueService;
import it.bugboard26.bugboard.modules.projects.HeaderRequestService;
import it.bugboard26.bugboard.modules.users.UserService;
import it.bugboard26.bugboard.users_micro_service.UserResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/projects/{uuid_project}/{uuid_issue}")
@CrossOrigin(origins = "https://app.bugboard26.it")
@RestController
public class EventApi {
    private HeaderRequestService headerRequest;
    private EventService eventService;
    private IssueService issueService;
    private JwtService jwtService;
    private UserService userService;

    @GetMapping("/events")
    public List<IssueEventResponse> getEventsByIssue(@PathVariable UUID uuid_issue) {
         if (!headerRequest.hasAuthorization()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");

        List<IssueEvent> events = eventService.getByIssueUuid(uuid_issue);
        return eventService.enrichEventsWithAuthors(events);
    }

    @PostMapping("/comment")
    public CommentResponse postNewComment(@PathVariable UUID uuid_issue, @RequestBody CommentRequest commentRequest) {
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
        return commentResponse;
    }

    @PatchMapping("/change")
    public ChangeResponse updateIssue(@PathVariable UUID uuid_issue, @RequestBody ChangeRequest changeRequest) {
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
        ChangeResponse changeResponse = ChangeResponse.map(change, author);
        return changeResponse;
    }
    
}
