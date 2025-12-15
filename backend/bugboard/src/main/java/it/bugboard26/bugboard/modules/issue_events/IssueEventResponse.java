package it.bugboard26.bugboard.modules.issue_events;

import java.time.LocalDateTime;

import it.bugboard26.bugboard.entities.Comment;
import it.bugboard26.bugboard.entities.IssueEvent;
import it.bugboard26.bugboard.enums.IssueEventType;
import it.bugboard26.bugboard.modules.issue_events.comments.CommentResponse;
import it.bugboard26.bugboard.users_micro_service.UserResponse;
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
                return null;    // TODO: implement Change Response mapping

            default:
                return null;
        }
    }

}
