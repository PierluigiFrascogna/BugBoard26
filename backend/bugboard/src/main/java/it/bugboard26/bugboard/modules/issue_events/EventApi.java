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

import it.bugboard26.bugboard.auth.Jwt;
import it.bugboard26.bugboard.entities.Change;
import it.bugboard26.bugboard.entities.Comment;
import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.IssueEvent;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.Role;
import it.bugboard26.bugboard.modules.issue_events.changes.dtos.request.ChangeRequest;
import it.bugboard26.bugboard.modules.issue_events.changes.dtos.response.ChangeResponse;
import it.bugboard26.bugboard.modules.issue_events.comments.dtos.CommentRequest;
import it.bugboard26.bugboard.modules.issue_events.comments.dtos.CommentResponse;
import it.bugboard26.bugboard.modules.issue_events.dtos.IssueEventResponse;
import it.bugboard26.bugboard.modules.issues.IssueService;
import it.bugboard26.bugboard.modules.users.UserService;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/projects/{uuid_project}/{uuid_issue}")
@CrossOrigin(origins = "https://app.bugboard26.it")
@RestController
public class EventApi {
    private Jwt jwtUser;
    private EventService eventService;
    private IssueService issueService;
    private UserService userService;

    @GetMapping("/events")
    public List<IssueEventResponse> getEventsByIssue(@PathVariable UUID uuid_issue) {
        List<IssueEvent> events = eventService.getByIssueUuid(uuid_issue);
        return eventService.enrichEventsWithAuthors(events);
    }

    @PostMapping("/comment")
    public CommentResponse postNewComment(@PathVariable UUID uuid_issue, @RequestBody @Valid CommentRequest commentRequest) {
        if(jwtUser.getRole() == Role.VIEWER)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient permissions");

        Issue issue = issueService.getByUuid(uuid_issue);
        User user = userService.getByUuid(jwtUser.getUserUuid());
        Comment newComment = eventService.saveComment(commentRequest, issue, user);

        UserResponse author = new UserResponse(jwtUser.getToken());
        CommentResponse commentResponse = CommentResponse.map(newComment, author);
        return commentResponse;
    }

    @PatchMapping("/change")
    public ChangeResponse updateIssue(@PathVariable UUID uuid_issue, @RequestBody ChangeRequest changeRequest) {
        if(jwtUser.getRole() == Role.VIEWER)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient permissions");

        Issue issue = issueService.getByUuid(uuid_issue);
        User user = userService.getByUuid(jwtUser.getUserUuid());
        Change change = eventService.saveChange(changeRequest, issue, user);

        UserResponse author = new UserResponse(jwtUser.getToken());
        ChangeResponse changeResponse = ChangeResponse.map(change, author);
        return changeResponse;
    }
    
}
