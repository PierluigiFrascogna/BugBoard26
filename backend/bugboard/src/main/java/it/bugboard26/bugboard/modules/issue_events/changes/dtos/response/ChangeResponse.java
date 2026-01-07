package it.bugboard26.bugboard.modules.issue_events.changes.dtos.response;

import it.bugboard26.bugboard.entities.Change;
import it.bugboard26.bugboard.entities.DescriptionChange;
import it.bugboard26.bugboard.entities.PriorityChange;
import it.bugboard26.bugboard.entities.StateChange;
import it.bugboard26.bugboard.entities.TitleChange;
import it.bugboard26.bugboard.enums.ChangeType;
import it.bugboard26.bugboard.modules.issue_events.IssueEventResponse;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import lombok.Getter;

// TODO: implement factory pattern for ChangeResponse subclasses
@Getter
public abstract class ChangeResponse extends IssueEventResponse {
    protected ChangeType changeType;
    
    public static ChangeResponse map(Change change, UserResponse author) {
        switch (change.getChangeType()) {
            case TITLE:
                return TitleChangeResponse.map((TitleChange) change, author);

            case DESCRIPTION:
                return DescriptionChangeResponse.map((DescriptionChange) change, author);

            case PRIORITY:
                return PriorityChangeResponse.map((PriorityChange) change, author);

            case STATE:
                return StateChangeResponse.map((StateChange) change, author);
        
            default:
                return null;
        }
    }
}
