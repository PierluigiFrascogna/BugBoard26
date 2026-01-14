package it.bugboard26.bugboard.modules.issues.dtos;

import java.time.LocalDate;
import java.util.UUID;

import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.enums.IssueState;
import it.bugboard26.bugboard.enums.IssueType;
import it.bugboard26.bugboard.enums.Priority;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class IssueResponse {
    private UUID uuid;
    private String title;
    private String description;
    private LocalDate createdAt;
    private IssueType type;
    private Priority priority;
    private IssueState state;
    private String imageUrl;
    private UserResponse author;


    public static IssueResponse map(Issue issue, UserResponse author) {
        IssueResponse response = new IssueResponse();
        response.uuid = issue.getUuid();
        response.title = issue.getTitle();
        response.description = issue.getDescription();
        response.createdAt = issue.getCreatedAt();
        response.type = issue.getType();
        response.priority = issue.getPriority();
        response.state = issue.getState();
        response.imageUrl = issue.getImageUrl();
        response.author = author;
        return response;
    }

}