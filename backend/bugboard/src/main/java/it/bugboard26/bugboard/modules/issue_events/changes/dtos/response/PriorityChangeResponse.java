package it.bugboard26.bugboard.modules.issue_events.changes.dtos.response;

import it.bugboard26.bugboard.entities.PriorityChange;
import it.bugboard26.bugboard.enums.Priority;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import lombok.Getter;

@Getter
public class PriorityChangeResponse extends ChangeResponse {
    private Priority oldPriority;
    private Priority newPriority;

    public static PriorityChangeResponse map(PriorityChange change, UserResponse author) {
        PriorityChangeResponse response = new PriorityChangeResponse();
        response.createdAt = change.getCreatedAt();
        response.type = change.getType();
        response.changeType = change.getChangeType();
        response.author = author;
        response.oldPriority = change.getOldPriority();
        response.newPriority = change.getNewPriority();
        return response;
    }
    
}
