package it.bugboard26.bugboard.dtos;

import it.bugboard26.bugboard.entities.Comment;
import lombok.Getter;

@Getter
public class CommentResponse extends IssueEventResponse {
    private String text;

    public static IssueEventResponse map(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.createdAt = comment.getCreatedAt();
        response.type = comment.getType();
        response.author = UserResponse.map(comment.getAuthor());
        response.text = comment.getText();
        return response;
    }
}
