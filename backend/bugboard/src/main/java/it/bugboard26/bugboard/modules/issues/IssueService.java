package it.bugboard26.bugboard.modules.issues;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.Project;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.modules.issues.dtos.IssueRequest;
import it.bugboard26.bugboard.modules.projects.ProjectRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class IssueService {
    private IssueRepository issueRepository;
    private ProjectRepository projectRepository;
    
    public Issue getByUuid(UUID issueUuid) {
        return issueRepository.findById(issueUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue not found"));
    }

    public List<Issue> getAllByProjectUuid(UUID projectUuid) {
        return issueRepository.findByProjectUuid(projectUuid);
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

}
