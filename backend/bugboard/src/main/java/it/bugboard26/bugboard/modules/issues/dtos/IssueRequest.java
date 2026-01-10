package it.bugboard26.bugboard.modules.issues.dtos;

import java.time.LocalDate;

import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.Project;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.IssueState;
import it.bugboard26.bugboard.enums.IssueType;
import it.bugboard26.bugboard.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private IssueType type;
    private Priority priority;
    private String imageUrl;


    public static Issue mapToEntity(IssueRequest issueRequest, User author, Project project) {
        Issue issue = new Issue();
        issue.setTitle(issueRequest.title);
        issue.setDescription(issueRequest.description);
        issue.setCreatedAt(LocalDate.now());
        issue.setType(issueRequest.type);
        issue.setPriority(issueRequest.priority);
        issue.setState(IssueState.TODO);
        issue.setImageUrl(issueRequest.imageUrl);
        issue.setAuthor(author);
        issue.setProject(project);
        return issue;
    }
}
