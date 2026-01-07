package it.bugboard26.bugboard.modules.issues;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.Project;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.modules.issues.dtos.IssueRequest;
import it.bugboard26.bugboard.modules.issues.dtos.IssueResponse;
import it.bugboard26.bugboard.modules.projects.ProjectRepository;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import it.bugboard26.bugboard.users_micro_service.UsersMicroService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class IssueService {
    private UsersMicroService usersMicroService;
    private IssueRepository issueRepository;
    private ProjectRepository projectRepository;
    
    public Issue getByUuid(UUID issueUuid) {
        return issueRepository.findById(issueUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue not found"));
    }

    public List<Issue> getIssuesByProject(UUID uuid_project, String type, String priority, String status) { 
        Specification<Issue> spec = Specification.where(IssueSpecification.hasProjectUuid(uuid_project));
        spec = spec.and(IssueSpecification.hasType(type))
                   .and(IssueSpecification.hasStatus(status))
                   .and(IssueSpecification.hasPriority(priority));

        return issueRepository.findAll(spec);
    }
    
    public Issue createIssue(IssueRequest request, UUID projectUuid, User author) {
        Project project = projectRepository.findById(projectUuid)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
    
        Issue newIssue = IssueRequest.mapToEntity(request, author, project);
        return issueRepository.save(newIssue);
    }

    public Issue save(Issue issue) {
        return issueRepository.save(issue);
    }

    public List<IssueResponse> enrichIssuesWithAuthors(List<Issue> issues) {
        if (issues.isEmpty()) return new ArrayList<>();

        Set<UUID> authorIds = issues.stream()
                .map(i -> i.getAuthor().getUuid())
                .collect(Collectors.toSet());

        Map<UUID, UserResponse> authors = usersMicroService.getUsersByIds(authorIds);

        return issues.stream()
                .map(issue -> {
                    UserResponse author = authors.get(issue.getAuthor().getUuid());
                    return IssueResponse.map(issue, author);
                })
                .collect(Collectors.toList());
    }

}
