package it.bugboard26.bugboard.modules.issue_events.comments;

import it.bugboard26.bugboard.entities.Comment;
import it.bugboard26.bugboard.modules.issue_events.IssueEventResponse;
import it.bugboard26.bugboard.users_micro_service.UserResponse;
import lombok.Getter;

@Getter
public class CommentResponse extends IssueEventResponse {
    private String text;

    public static IssueEventResponse map(Comment comment, UserResponse author) {
        CommentResponse response = new CommentResponse();
        response.createdAt = comment.getCreatedAt();
        response.type = comment.getType();
        response.author = author;
        response.text = comment.getText();
        return response;
    }
}
