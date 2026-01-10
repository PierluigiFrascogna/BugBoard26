package it.bugboard26.bugboard.modules.issue_events.dtos;

import java.time.LocalDateTime;

import it.bugboard26.bugboard.entities.Change;
import it.bugboard26.bugboard.entities.Comment;
import it.bugboard26.bugboard.entities.IssueEvent;
import it.bugboard26.bugboard.enums.IssueEventType;
import it.bugboard26.bugboard.modules.issue_events.changes.dtos.response.ChangeResponse;
import it.bugboard26.bugboard.modules.issue_events.comments.dtos.CommentResponse;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import lombok.Getter;

// TODO: implement factory pattern for IssueEventResponse subclasses
@Getter
public abstract class IssueEventResponse{
    protected LocalDateTime createdAt;    
    protected IssueEventType type;
    protected UserResponse author;

    public static IssueEventResponse map(IssueEvent event, UserResponse author) {
        switch (event.getType()) {
            case COMMENT:
                return CommentResponse.map((Comment) event, author);
        
            case CHANGE:
                return ChangeResponse.map((Change) event, author);

            default:
                return null;
        }
    }

}
