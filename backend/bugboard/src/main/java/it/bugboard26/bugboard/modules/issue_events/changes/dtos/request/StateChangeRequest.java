package it.bugboard26.bugboard.modules.issue_events.changes.dtos.request;

import it.bugboard26.bugboard.entities.Change;
import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.StateChange;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.ChangeType;
import it.bugboard26.bugboard.enums.IssueState;
import lombok.Getter;

@Getter
public class StateChangeRequest implements ChangeRequest{
    private ChangeType type = ChangeType.STATE;
    private IssueState newState;

    @Override
    public Change mapToEntity(Issue issue, User author) {
        StateChange change = new StateChange();
        change.setOldState(issue.getState());
        change.setNewState(newState);
        change.setIssue(issue);
        change.setAuthor(author);
        return change;
    }
    
}
