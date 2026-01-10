package it.bugboard26.bugboard.modules.issue_events.comments.dtos;

import it.bugboard26.bugboard.entities.Comment;
import it.bugboard26.bugboard.modules.issue_events.dtos.IssueEventResponse;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import lombok.Getter;

@Getter
public class CommentResponse extends IssueEventResponse {
    private String text;

    public static CommentResponse map(Comment comment, UserResponse author) {
        CommentResponse response = new CommentResponse();
        response.createdAt = comment.getCreatedAt();
        response.type = comment.getType();
        response.author = author;
        response.text = comment.getText();
        return response;
    }
}
