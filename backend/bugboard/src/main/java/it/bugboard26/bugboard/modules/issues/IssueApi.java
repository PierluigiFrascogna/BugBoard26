package it.bugboard26.bugboard.modules.issues;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.Role;
import it.bugboard26.bugboard.modules.auth.JwtService;
import it.bugboard26.bugboard.modules.issues.dtos.IssueRequest;
import it.bugboard26.bugboard.modules.issues.dtos.IssueResponse;
import it.bugboard26.bugboard.modules.projects.HeaderRequestService;
import it.bugboard26.bugboard.modules.users.UserService;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@CrossOrigin(origins = "https://app.bugboard26.it")
@RequestMapping("/projects/{uuid_project}")
@RestController
public class IssueApi {
    private HeaderRequestService headerRequest;
    private JwtService jwtService;
    private IssueService issueService;
    private UserService userService;

    @GetMapping("/issues")
    public List<IssueResponse> getIssuesByProject(
            @PathVariable UUID uuid_project,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String state) {

        if (!headerRequest.hasAuthorization()) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");

        List<Issue> issues = issueService.getIssuesByProject(uuid_project, type, priority, state);
        return issueService.enrichIssuesWithAuthors(issues);
    }

    @PostMapping("/issues")
    public IssueResponse postNewIssue(@PathVariable UUID uuid_project, @RequestBody IssueRequest issueRequest) {
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
        return issueResponse;
    }
}
