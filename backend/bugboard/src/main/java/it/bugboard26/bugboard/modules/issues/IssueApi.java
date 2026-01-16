package it.bugboard26.bugboard.modules.issues;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.bugboard26.bugboard.auth.Jwt;
import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.Project;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.Role;
import it.bugboard26.bugboard.modules.issues.dtos.IssueRequest;
import it.bugboard26.bugboard.modules.issues.dtos.IssueResponse;
import it.bugboard26.bugboard.modules.projects.ProjectService;
import it.bugboard26.bugboard.modules.users.UserService;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/projects/{uuid_project}")
@RestController
public class IssueApi {
    private Jwt jwtUser;
    private IssueService issueService;
    private UserService userService;
    private ProjectService projectService;

    @GetMapping("/issues")
    public ResponseEntity<List<IssueResponse>> getIssuesByProject(
        @PathVariable UUID uuid_project,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String priority,
        @RequestParam(required = false) String state
    ) {
        Project project = projectService.getByUuid(uuid_project).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found")
        );

        List<Issue> issues = issueService.getIssuesByProject(project, type, priority, state);
        return ResponseEntity.ok(issueService.enrichIssuesWithAuthors(issues));
    }

    @PostMapping("/issues")
    public ResponseEntity<IssueResponse> postNewIssue(@PathVariable UUID uuid_project, @RequestBody @Valid IssueRequest issueRequest) {
        if (jwtUser.getRole() == Role.VIEWER) 
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient permissions");
        
        Project project = projectService.getByUuid(uuid_project).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found")
        );
        User user = userService.getByUuid(jwtUser.getUserUuid());
        
        Issue newIssue = issueService.createIssue(issueRequest, user, project);
        IssueResponse issueResponse = IssueResponse.map(newIssue, new UserResponse(jwtUser.getToken()));
        return ResponseEntity.status(HttpStatus.CREATED).body(issueResponse);
    }
}
