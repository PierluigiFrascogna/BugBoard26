package it.bugboard26.bugboard.modules.issue_events.comments.dtos;

import it.bugboard26.bugboard.entities.Comment;
import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.IssueEventType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequest {
    @NotBlank
    private String text;

    public static Comment mapToEntity(CommentRequest commentRequest, Issue issue, User author) {
        Comment commentEntity = new Comment();
        commentEntity.setType(IssueEventType.COMMENT);
        commentEntity.setText(commentRequest.text);
        commentEntity.setAuthor(author);
        commentEntity.setIssue(issue);
        return commentEntity;
    }
}
