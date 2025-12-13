package it.bugboard26.bugboard.dtos;

import java.time.LocalDateTime;

import it.bugboard26.bugboard.entities.Comment;
import it.bugboard26.bugboard.entities.IssueEvent;
import it.bugboard26.bugboard.enums.IssueEventType;
import lombok.Getter;

@Getter
public abstract class IssueEventResponse{
    protected LocalDateTime createdAt;    
    protected IssueEventType type;
    protected UserResponse author;

    public static IssueEventResponse map(IssueEvent event) {
        switch (event.getType()) {
            case COMMENT:
                return CommentResponse.map((Comment) event);
        
            case CHANGE:
                return null;    //TODO: implement Change Response mapping

            default:
                return null;
        }
    }

}
