package it.bugboard26.bugboard.modules.issue_events.changes.dtos.response;

import it.bugboard26.bugboard.entities.StateChange;
import it.bugboard26.bugboard.enums.IssueState;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import lombok.Getter;

@Getter
public class StateChangeResponse extends ChangeResponse {
    private IssueState oldState;
    private IssueState newState;

    public static StateChangeResponse map(StateChange change, UserResponse author) {
        StateChangeResponse response = new StateChangeResponse();
        response.createdAt = change.getCreatedAt();
        response.type = change.getType();
        response.changeType = change.getChangeType();
        response.author = author;
        response.oldState = change.getOldState();
        response.newState = change.getNewState();
        return response;
    }
    
}
