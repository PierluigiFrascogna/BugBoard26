package it.bugboard26.bugboard.modules.issues;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import it.bugboard26.bugboard.entities.Issue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class IssueService {
    private IssueRepository issueRepository;

    public List<Issue> getAllByProjectUuid(UUID projectUuid) {
        return issueRepository.findByProjectUuid(projectUuid);
    }
}
